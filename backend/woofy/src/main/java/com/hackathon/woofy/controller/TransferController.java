package com.hackathon.woofy.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hackathon.woofy.entity.Child;
import com.hackathon.woofy.entity.Parent;
import com.hackathon.woofy.entity.Transinfo;
import com.hackathon.woofy.entity.TransinfoStatus;
import com.hackathon.woofy.entity.User;
import com.hackathon.woofy.request.TransRequest;
import com.hackathon.woofy.response.BasicResponse;
import com.hackathon.woofy.service.ChildService;
import com.hackathon.woofy.service.ParentService;
import com.hackathon.woofy.service.RedisService;
import com.hackathon.woofy.service.TransinfoService;
import com.hackathon.woofy.service.UserService;
import com.hackathon.woofy.util.WooriFunc;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transfer")
public class TransferController {

	private final ParentService parentService;
	private final ChildService childService;
	private final TransinfoService transinfoService;
	private final WooriFunc wooriFunc = new WooriFunc();
	
	@Autowired RedisService redisService;
	@Autowired PasswordEncoder passwordEncoder;
	
	@Secured({"ROLE_PARENT", "ROLE_CHILD"})
	@PostMapping(value = "", produces = "application/json; charset=utf8")
	public Object executeTransAction(@RequestBody TransRequest transRequest) {
		final BasicResponse basicResponse = new BasicResponse();

		// 1. ?????? ????????? ??? ????????? ????????? ????????????.
		User authUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String targetUserSecretCode = authUser.getSecretCode();
		
		Parent parent = null;
		Child child = null;
		
		Transinfo transinfo  = new Transinfo(); // ???????????? ??????;
		boolean isChild = false; // ???????????? ???????????? ??????
		boolean isLimit = false; // ?????????????????? ??????
		String WDR_ACNO = ""; // ?????? ?????? ??????
		
		String usernameInCode = redisService.getHashSetItem("TransferSessionCodeTable", transRequest.getTransCode());

		if (usernameInCode == null) {
			basicResponse.status = "404";
			return new ResponseEntity<>(basicResponse, HttpStatus.NOT_FOUND);
		}
		
//		if (!transRequest.getSecretCode().equals(usernameInCode)) {
//			basicResponse.status = "400";
//			return new ResponseEntity<>(basicResponse, HttpStatus.BAD_REQUEST);
//		}

		
		if (!passwordEncoder.matches(transRequest.getSecretCode(), targetUserSecretCode)) {
			basicResponse.status = "400";
			return new ResponseEntity<>(basicResponse, HttpStatus.BAD_REQUEST);
		}

		
		String userRole = authUser.getRoles().iterator().next();
		
		try {
			Map<String, Object> map = new HashMap<>();

			if (userRole.equals("ROLE_PARENT")) { // ????????? ????????? ???
				parent = parentService.findByUser(authUser);
				WDR_ACNO = parent.getAccount();
			} else { 
				// ????????? ????????? ???
				isChild = true;
				
				child = childService.findByUser(authUser);
				WDR_ACNO = child.getParent().getAccount();

				if (child.getSpendLimit() < transRequest.getPrice()) { 
					// ?????? ????????? ???
					isLimit = true;
					
					transinfo.setDate(LocalDateTime.now());
					transinfo.setLocation(transRequest.getTransMessage());
					transinfo.setPrice(transRequest.getPrice());
					transinfo.setChildNum(child.getId());
					transinfo.setParent(child.getParent());
					transinfo.setTransinfoStatus(TransinfoStatus.FAIL);
					
					transinfoService.saveTransinfo(transinfo);
					
					// ??????????????? ?????? ????????? ???????????? ?????? ???????????? ?????????
					
					basicResponse.dataBody = transinfo;
					basicResponse.status = "limit";
				} 
			}
			
			if(!isLimit) {
				if (transRequest.getBankCode().equals("020")) {
					wooriFunc.executeWooriAcctToWooriAcct
					(WDR_ACNO, Integer.toString(transRequest.getPrice()), 
							transRequest.getBankCode(), transRequest.getAccountNumber(), 
							transRequest.getTransMessage());
				} else {
					wooriFunc.executeWooriAcctToOtherAcct
					(WDR_ACNO, Integer.toString(transRequest.getPrice()), 
							transRequest.getBankCode(), transRequest.getAccountNumber(), 
							transRequest.getTransMessage());
				}
				
				transinfo.setDate(LocalDateTime.now());
				transinfo.setLocation(transRequest.getTransMessage());
				transinfo.setPrice(transRequest.getPrice());
				
				if(isChild) {
					transinfo.setChildNum(child.getId());
					transinfo.setParent(child.getParent());
					child.decreaseSpendLimit(transRequest.getPrice());
					childService.saveChild(child);
				} else {
					transinfo.setParent(parent);
				}
				
				transinfo.setTransinfoStatus(TransinfoStatus.SUCCESS);
				transinfoService.saveTransinfo(transinfo);
				
				basicResponse.dataBody = transinfo;
				basicResponse.status = "success";
			}
		} catch (Exception e) {
			basicResponse.status = "error";
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(basicResponse, HttpStatus.OK);
	}

	@Secured({"ROLE_PARENT", "ROLE_CHILD"})
	@GetMapping(value = "", produces = "application/json; charset=utf8")
	public Object generateTransferCode() {
		final BasicResponse basicResponse = new BasicResponse();
		
		// 1. ?????? ????????? ??? ????????? ????????? ????????????.
		User authUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String targetUsername = authUser.getUsername();
		
		// 2. ?????? ????????? ?????? ????????? ???????????? ?????? ????????? ?????? ????????? ????????????, ????????? ?????? ??? ?????????, ????????? ?????? ???????????????.
		// ????????? ????????? ?????? ??????: ?????? / ??????: ????????? ???????????? ????????????.
		
		try {
			String checkPriviousCode = redisService.getHashSetItem("TransferSessionUserTable", targetUsername);
			
			if (checkPriviousCode != null) {
				redisService.dropHashSetItem("TransferSessionCodeTable", checkPriviousCode);
			}

			String targetRequestCode = "FQ" + RandomStringUtils.randomNumeric(18);
			
			redisService.insertHashTableContent("TransferSessionCodeTable", targetRequestCode, targetUsername);
			redisService.setHashSetTimeLimit("TransferSessionCodeTable", targetRequestCode, 180);

			redisService.insertHashTableContent("TransferSessionUserTable", targetUsername, targetRequestCode);
			redisService.setHashSetTimeLimit("TransferSessionUserTable", targetUsername, 180);
						
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("code", targetRequestCode);
			basicResponse.dataBody = jsonObject;
		} catch (Exception e) {
			basicResponse.status = "500";
			e.printStackTrace();
			return new ResponseEntity<>(basicResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(basicResponse, HttpStatus.CREATED);
	}


}
