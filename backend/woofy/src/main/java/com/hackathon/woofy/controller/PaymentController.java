package com.hackathon.woofy.controller;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hackathon.woofy.entity.User;
import com.hackathon.woofy.response.BasicResponse;
import com.hackathon.woofy.service.RedisService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payments")
public class PaymentController {
	@Autowired RedisService redisService;

	@Secured({"ROLE_PARENT", "ROLE_CHILD"})
	@PostMapping(value = "", produces = "application/json; charset=utf8")
	public Object generatePayCode() {
		final BasicResponse basicResponse = new BasicResponse();
		
		// 1. 해당 요청을 한 유저의 정보를 가져온다.
		User authUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String targetUsername = authUser.getUsername();
		
		// 2. 해당 유저의 결제 코드를 발급하기 전에 기존의 결제 세션을 검사하고, 있으면 삭제 후 재발급, 아니면 그냥 재발급이다.
		// 편리한 구현을 위해 유저: 코드 / 코드: 유저로 이중으로 관리한다.
		
		try {
			String checkPriviousCode = redisService.getHashSetItem("PaymentSessionUserTable", targetUsername);
			
			if (checkPriviousCode != null) {
				redisService.dropHashSetItem("PaymentSessionCodeTable", checkPriviousCode);
			}

			String targetRequestCode = "PQ" + RandomStringUtils.randomNumeric(18);
			
			redisService.insertHashTableContent("PaymentSessionCodeTable", targetRequestCode, targetUsername);
			redisService.setHashSetTimeLimit("PaymentSessionCodeTable", targetRequestCode, 180);

			redisService.insertHashTableContent("PaymentSessionUserTable", targetUsername, targetRequestCode);
			redisService.setHashSetTimeLimit("PaymentSessionUserTable", targetUsername, 180);
						
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
