package com.hackathon.woofy.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hackathon.woofy.entity.Suspicious;
import com.hackathon.woofy.request.SuspiciousRequest;
import com.hackathon.woofy.service.SuspiciousService;
import com.hackathon.woofy.util.BasicResponse;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/suspicious")
public class SuspiciousController {

	private final SuspiciousService suspiciousService;
	
	@PostMapping("/add")
	public Object add(@RequestBody SuspiciousRequest suspiciousRequest) {
		final BasicResponse basicResponse = new BasicResponse();
		
		try {
			Map<String, Object> map = new HashMap<>();
			
			Suspicious result = new Suspicious(suspiciousRequest);
			suspiciousService.addSuspicious(result);
			
			map.put("suspicious", result);
			basicResponse.dataBody = map;
			basicResponse.data = "success";
			basicResponse.status = true;
			
		} catch (Exception e) {
			basicResponse.data = "error";
			basicResponse.status = false;
			e.printStackTrace();
		} finally {
			return new ResponseEntity<>(basicResponse, HttpStatus.OK);
		}
	}
}
