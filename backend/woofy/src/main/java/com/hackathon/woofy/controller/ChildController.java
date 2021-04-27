package com.hackathon.woofy.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hackathon.woofy.entity.Child;
import com.hackathon.woofy.entity.Parent;
import com.hackathon.woofy.request.UserRequest;
import com.hackathon.woofy.service.ChildService;
import com.hackathon.woofy.service.ParentService;
import com.hackathon.woofy.util.BasicResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/child")
public class ChildController {

	private final ChildService childService;
	private final ParentService parentService;

	@PostMapping("/signup")
	public Object signup(@RequestBody UserRequest userRequest) {

		final BasicResponse basicResponse = new BasicResponse();

		try {
			Map<String, Object> map = new HashMap<>();
			
			// 일단 테스트용 부모
			Parent parent = parentService.findParent("01012341234");
			System.out.println("parent: " + parent.getId());
			
			Child child = new Child();
			child.setUsername(userRequest.getUsername());
			child.setPassword(userRequest.getPassword());
			child.setFirstName(userRequest.getFirstName());
			child.setLastName(userRequest.getLastName());
			child.setPhoneNumber(userRequest.getPhoneNumber());
			child.setParent(parent);
			
			Child result = childService.saveChild(child);
			System.out.println("result: " + result);
			
			System.out.println("CHILD: "+child.getParent());

			map.put("child", result);
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