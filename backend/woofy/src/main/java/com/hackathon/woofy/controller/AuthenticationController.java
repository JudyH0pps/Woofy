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
import org.json.simple.JSONArray;
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
import com.hackathon.woofy.entity.Child;
import com.hackathon.woofy.entity.Parent;
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
	
	@Autowired
	ParentService parentService;
	
	@Autowired
	ChildService childService;
	
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
	public ResponseEntity<JSONObject> executeCellCerti(@RequestBody Map<String, Object> jsonRequest) throws ParseException {
		String requestType = (String)jsonRequest.get("type");
		Map<String, Object> cellCretiRequestBody = (Map<String, Object>) jsonRequest.get("dataBody");
		System.out.println(requestType);
		System.out.println(cellCretiRequestBody);
		
		JSONObject responseObject = new JSONObject();
		
		// SMS 인증번호(개발 단계에서 암호화/복호화 안함)
		String SMS_CRTF_NO = (String)cellCretiRequestBody.get("ENCY_SMS_CRTF_NO");
		String CRTF_UNQ_NO = (String)redisService.getHashSetItem("CellCertiTable", SMS_CRTF_NO);
		String HP_NO = (String)cellCretiRequestBody.get("HP_NO");
		
		if (CRTF_UNQ_NO == null) {
			responseObject.put("status", 400);
			return new ResponseEntity<>(responseObject, HttpStatus.BAD_REQUEST);
		}
		
		String result = "";
		
		try {
			result = wooriFunc.executeCellCerti(
					(String)cellCretiRequestBody.get("RRNO_BFNB"), 
					(String)cellCretiRequestBody.get("ENCY_RRNO_LSNM"),
					SMS_CRTF_NO,
					CRTF_UNQ_NO
					);
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException
				| IOException e) {
			e.printStackTrace();
			result = "FAILED";
		}
		
		// 해당 SMS 인증 정보를 찾는데 실패했다.
		if (result.equals("FAILED")) {
			responseObject.put("status", 400);
			return new ResponseEntity<>(responseObject, HttpStatus.BAD_REQUEST);
		}
		
		System.out.println(result);
		
		JSONParser parser = new JSONParser();
		JSONObject targetDataBody = (JSONObject)((JSONObject)parser.parse(result)).get("dataBody");
		
		// 현재 요청 타입이 부모 가입("1")이면, 부모를 휴대폰 번호로 찾는다. 없으면 가입하라는 메시지를 남기자.
		if (requestType.equals("1")) {
			Parent searchParent = parentService.findbyPhoneNumber(HP_NO);
			if (searchParent == null) {
				// 찾고자 하는 부모가 없다. 새로 가입해야 한다.
				JSONArray jsonArray = (JSONArray)targetDataBody.get("REPT_FA");
				JSONObject jsonObject = (JSONObject)jsonArray.get(0);
				String targetAccountNumber = (String)jsonObject.get("CUS_USG_ACNO");
				
				// 회원가입시 CRTF 데이터 인증용 테이블에 저장한다.
				redisService.insertHashTableContent("ParentJoinCRTFTable", CRTF_UNQ_NO, HP_NO);
				redisService.setHashSetTimeLimit("ParentJoinCRTFTable", CRTF_UNQ_NO, 900);
				
				// 회원가입시 계좌번호를 확인하는 용도로 사용된다. 우리은행의 현업 API는 계좌 확인 기능을 사용하고 있지 않다. 그 점을 감안해서 로직은 축소하겠다.
				redisService.insertHashTableContent("ParentJoinAccTable", CRTF_UNQ_NO, targetAccountNumber.substring(0, 8));
				redisService.setHashSetTimeLimit("ParentJoinAccTable", CRTF_UNQ_NO, 900);
				
				responseObject.put("status", 404);
				responseObject.put("CRTF_UNQ_NO", CRTF_UNQ_NO);	// CRTF_UNQ_NO는 부모 회원가입 시에 유효성 검증에 사용된다.
				responseObject.put("CUS_USG_ACNO", targetAccountNumber);
				return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);	// 404 메시지를 내보내서 프론트엔드에서 회원가입 페이지로 이동하는데 활용하게 한다.
			}
		}
		
		else {
			Child searchChild = childService.findByPhoneNumber(HP_NO);
			if (searchChild == null) {
				// 자녀의 SMS 인증은 사후 인증이다. 여기에서는 별다른 로직 구현 없이 별도의 처리를 진행한다.
				responseObject.put("status", 204);
				return new ResponseEntity<>(responseObject, HttpStatus.NO_CONTENT);	// 이곳의 204 에러는 자녀가 없다는 의미이다.
			}
			
			// TO-DO: 자녀의 SMS 인증 여부를 True로 변경하고 저장한다. 자녀 Entity의 테이블 구성에 따라 로직을 구성하도록 한다.
		}
		
		responseObject.put("status", 200);
		return new ResponseEntity<>(responseObject, HttpStatus.ACCEPTED);
	}
}
