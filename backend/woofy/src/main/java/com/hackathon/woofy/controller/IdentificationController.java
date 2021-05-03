package com.hackathon.woofy.controller;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hackathon.woofy.config.security.JwtTokenProvider;
import com.hackathon.woofy.entity.User;
import com.hackathon.woofy.response.BasicResponse;
import com.hackathon.woofy.service.UserService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/signin")
public class IdentificationController {
	private final UserService userService;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;
	
	@PostMapping(value = "")
	public Object signin(@RequestBody Map<String, Object> jsonRequest) {
		final BasicResponse basicResponse = new BasicResponse();
		
		Map<String, Object> signinRequestBody = (Map<String, Object>) jsonRequest.get("dataBody");
		String targetUsername = (String)signinRequestBody.get("username");
		String targetPassword = (String)signinRequestBody.get("password");
		
		User targetUser = userService.findByUsername(targetUsername);
		
		if (targetUser == null) {
			basicResponse.status = "404";
			return new ResponseEntity<>(basicResponse, HttpStatus.NOT_FOUND);
		}
		
		if (!passwordEncoder.matches(targetPassword, targetUser.getPassword())) {
			basicResponse.status = "404";
			return new ResponseEntity<>(basicResponse, HttpStatus.NOT_FOUND);
		}
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("role", targetUser.getRoles().iterator().next());
		jsonObject.put("token", jwtTokenProvider.createToken(String.valueOf(targetUser.getId()), targetUser.getRoles()));
		
		basicResponse.status = "200";
		basicResponse.dataBody = jsonObject;
		
		return new ResponseEntity<>(basicResponse, HttpStatus.OK);
	}
}
