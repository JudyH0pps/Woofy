package com.hackathon.woofy.controller;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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
import com.hackathon.woofy.util.WooriFunc;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
	WooriFunc wooriFunc = new WooriFunc();
	
	@PostMapping("/getCellCerti")
	public ResponseEntity<JSONObject> getCellCerti(@RequestBody Map<String, Object> jsonRequest) {
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
		
		if (result.equals("FAILED")) {
			JSONObject responseObject = new JSONObject();
			responseObject.put("status", 400);
			return new ResponseEntity<>(responseObject, HttpStatus.BAD_REQUEST);
		}
		
		// 현재 개발 단계인 관계로 API 요청을 그대로 반환하는 형태이지만 실제로는 다음의 로직을 거친다.
		
		// 1. 받아온 CRTF_UTF_NO 값을 가져온다.
		// 2. 숫자 여섯자리로 구성된 임의의 SMS 인증 코드를 생성한다.
		// 3. KeyDB(Redis)에 부모키(CellCertiAuth)에 CRTF 값을 Child Key로 지정하고, 임의의 SMS 인증 코드를 값으로 하는 레코드를 생성한다.
		// 4. RequestBody로부터 휴대전화 번호를 가져온다.
		// 5. 해당 휴대 전화 번호로 SMS 인증 번호를 첨부하고 SMS를 전송한다.
		// 6. SMS 전송을 완료했다는 메시지가 담긴 JSONObject를 반환한다. 
		
		JSONParser parser = new JSONParser();
		Object obj = null;
		try {
			obj = parser.parse(result);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		JSONObject jsonObj = (JSONObject) obj;
		
		return new ResponseEntity<>(jsonObj, HttpStatus.ACCEPTED);
	}

	@PostMapping("/executeCellCerti")
	public String executeCellCerti(@RequestBody Map<String, Object> jsonRequest) {
		String requestType = (String)jsonRequest.get("type");
		Map<String, Object> cellCretiRequestBody = (Map<String, Object>) jsonRequest.get("dataBody");
		System.out.println(requestType);
		System.out.println(cellCretiRequestBody);
		
		String result = "";
		
		return result;
	}
}
