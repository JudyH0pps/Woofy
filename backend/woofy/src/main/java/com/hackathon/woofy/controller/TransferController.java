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

		// 1. 해당 요청을 한 유저의 정보를 가져온다.
		User authUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String targetUserSecretCode = authUser.getSecretCode();
		
		Parent parent = null;
		Child child = null;
		
		Transinfo transinfo  = new Transinfo(); // 거래내역 저장;
		boolean isChild = false; // 부모인지 자식인지 판별
		boolean isLimit = false; // 한도초과인지 확인
		String WDR_ACNO = ""; // 출금 계좌 번호
		
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

			if (userRole.equals("ROLE_PARENT")) { // 부모가 거래할 시
				parent = parentService.findByUser(authUser);
				WDR_ACNO = parent.getAccount();
			} else { 
				// 자식이 거래할 시
				isChild = true;
				
				child = childService.findByUser(authUser);
				WDR_ACNO = child.getParent().getAccount();

				if (child.getSpendLimit() < transRequest.getPrice()) { 
					// 한도 초과일 때
					isLimit = true;
					
					transinfo.setDate(LocalDateTime.now());
					transinfo.setLocation(transRequest.getTransMessage());
					transinfo.setPrice(transRequest.getPrice());
					transinfo.setChildNum(child.getId());
					transinfo.setParent(child.getParent());
					transinfo.setTransinfoStatus(TransinfoStatus.FAIL);
					
					transinfoService.saveTransinfo(transinfo);
					
					// 추가적으로 한도 넘으면 부모에게 알림 할지말지 정하자
					
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
		
		// 1. 해당 요청을 한 유저의 정보를 가져온다.
		User authUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String targetUsername = authUser.getUsername();
		
		// 2. 해당 유저의 결제 코드를 발급하기 전에 기존의 결제 세션을 검사하고, 있으면 삭제 후 재발급, 아니면 그냥 재발급이다.
		// 편리한 구현을 위해 유저: 코드 / 코드: 유저로 이중으로 관리한다.
		
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
