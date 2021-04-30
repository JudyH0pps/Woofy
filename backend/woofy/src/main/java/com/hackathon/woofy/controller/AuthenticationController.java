package com.hackathon.woofy.controller;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.hackathon.woofy.service.ChildService;
import com.hackathon.woofy.service.MissionService;
import com.hackathon.woofy.service.ParentService;
import com.hackathon.woofy.service.RedisService;
import com.hackathon.woofy.util.SmsFunc;
import com.hackathon.woofy.util.WooriFunc;

import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
	WooriFunc wooriFunc = new WooriFunc();
	SmsFunc smsFunc = new SmsFunc();
	
	@Autowired
	RedisService redisService;
	
	@PostMapping("/getCellCerti")
	public ResponseEntity<JSONObject> getCellCerti(@RequestBody Map<String, Object> jsonRequest) throws ParseException {
		String requestType = (String)jsonRequest.get("type");
		Map<String, Object> cellCretiRequestBody = (Map<String, Object>) jsonRequest.get("dataBody");
		System.out.println(requestType);
		System.out.println(cellCretiRequestBody);
		
		String result = "";
		
		try {
			result = wooriFunc.getCellCerti((String)cellCretiRequestBody.get("COMC_DIS"), 
					(String)cellCretiRequestBody.get("HP_NO"), 
					(String)cellCretiRequestBody.get("HP_CRTF_AGR_YN"), 
					(String)cellCretiRequestBody.get("FNM"), 
					(String)cellCretiRequestBody.get("RRNO_BFNB"), 
					(String)cellCretiRequestBody.get("ENCY_RRNO_LSNM"));
		} catch (Exception e) {
			e.printStackTrace();
			result = "FAILED";
		}

		JSONObject responseObject = new JSONObject();
		
		if (result.equals("FAILED")) {
			responseObject.put("status", 400);
			return new ResponseEntity<>(responseObject, HttpStatus.BAD_REQUEST);
		}
		
		// 현재 개발 단계인 관계로 API 요청을 그대로 반환하는 형태이지만 실제로는 다음의 로직을 거친다.
				
		JSONParser parser = new JSONParser();
		JSONObject targetDataBody = (JSONObject) ((JSONObject)parser.parse(result)).get("dataBody");
		
		String CRTF_UNQ_NO = (String) targetDataBody.get("CRTF_UNQ_NO");		
		String SMS_CRTF_NO = RandomStringUtils.randomNumeric(6); // Woori API를 통해 SMS가 전송되어야 하나, 테스트베드에서는 해당 기능이 제한적으로 제공되므로 프로젝트를 위해 임의의 SMS 넘버를 생성하기로 함.
		String HP_NO = (String)cellCretiRequestBody.get("HP_NO");
		String targetMessage = "WooFi(가제) SMS 인증번호는 [" + SMS_CRTF_NO + "] 입니다.";
		
		redisService.insertHashTableContent("CellCertiTable", SMS_CRTF_NO, CRTF_UNQ_NO);
		redisService.setHashSetTimeLimit("CellCertiTable", SMS_CRTF_NO, 180);
		
		smsFunc.sendMessage(HP_NO, targetMessage);		
		responseObject.put("status", 200);
		
		return new ResponseEntity<>(responseObject, HttpStatus.ACCEPTED);
	}

	@PostMapping("/executeCellCerti")
	public String executeCellCerti(@RequestBody Map<String, Object> jsonRequest) {
		String requestType = (String)jsonRequest.get("type");
		Map<String, Object> cellCretiRequestBody = (Map<String, Object>) jsonRequest.get("dataBody");
		System.out.println(requestType);
		System.out.println(cellCretiRequestBody);
		
		JSONObject responseObject = new JSONObject();
		
		
		String result = "";
		
		return result;
	}
}
