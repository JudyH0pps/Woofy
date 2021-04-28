package com.hackathon.woofy.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hackathon.woofy.service.ChildService;
import com.hackathon.woofy.service.MissionService;
import com.hackathon.woofy.service.ParentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
	@PostMapping("/getCellCerti")
	public String getCellCerti(@RequestBody Map<String, Object> jsonRequest) {
		String requestType = (String)jsonRequest.get("type");
		Map<String, Object> cellCretiRequestBody = (Map<String, Object>) jsonRequest.get("dataBody");
		System.out.println(requestType);
		System.out.println(cellCretiRequestBody);
		
		return "DEBUG";
	}

	@PostMapping("/executeCellCerti")
	public String executeCellCerti(@RequestBody Map<String, Object> jsonRequest) {
		String requestType = (String)jsonRequest.get("type");
		Map<String, Object> cellCretiRequestBody = (Map<String, Object>) jsonRequest.get("dataBody");
		System.out.println(requestType);
		System.out.println(cellCretiRequestBody);
		
		return "DEBUG";
	}
}
