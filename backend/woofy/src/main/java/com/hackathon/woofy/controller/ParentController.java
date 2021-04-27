package com.hackathon.woofy.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hackathon.woofy.entity.Parent;
import com.hackathon.woofy.request.UserRequest;
import com.hackathon.woofy.service.ParentService;
import com.hackathon.woofy.util.BasicResponse;

@RestController
@RequestMapping("/parent")
public class ParentController {
	
	@Autowired ParentService parentService;

	@PostMapping("/signup")
	public Object signup(@RequestBody UserRequest userRequest) {

		final BasicResponse basicResponse = new BasicResponse();
		
		try {
			Map<String, Object> map = new HashMap<>();
			Parent parent = new Parent(userRequest);
			Parent result = parentService.saveParent(parent);
			
			map.put("parent", result);
			basicResponse.object = map;
			basicResponse.data = "success";
			basicResponse.status = true;
			
		} catch(Exception e) {
			basicResponse.data = "error";
			basicResponse.status = true;
			e.printStackTrace();
		} finally {
			return new ResponseEntity<>(basicResponse, HttpStatus.OK);
		}
	}
}
