package com.hackathon.woofy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hackathon.woofy.entity.Child;
import com.hackathon.woofy.entity.Parent;
import com.hackathon.woofy.request.UserRequest;
import com.hackathon.woofy.response.BasicResponse;
import com.hackathon.woofy.service.ChildService;
import com.hackathon.woofy.service.ParentService;

@RestController
@RequestMapping("/api/v1/parent")
public class ParentController {
	
	@Autowired ParentService parentService;
	@Autowired ChildService childService;

	@PostMapping("/signup")
	public Object signup(@RequestBody UserRequest userRequest) {

		final BasicResponse basicResponse = new BasicResponse();
		
		try {
			Map<String, Object> map = new HashMap<>();
			
			Parent parent = new Parent(userRequest);
			
			parent.setAccount("123325421542353245324");
			
			Parent result = parentService.saveParent(parent);
			
			map.put("parent", result);
			basicResponse.dataBody = map;
			basicResponse.data = "success";
			basicResponse.status = true;
			
		} catch(Exception e) {
			basicResponse.data = "error";
			basicResponse.status = false;
			e.printStackTrace();
		} finally {
			return new ResponseEntity<>(basicResponse, HttpStatus.OK);
		}
	}
	
	/*
	@GetMapping("{username}/child")
	public Object findChildByParent(@PathVariable(name = "username") String username) {
		
		final BasicResponse basicResponse = new BasicResponse();
		
		try {
			Map<String, Object> map = new HashMap<>();

			Parent parent = parentService.findParent(username);
			List<Child> result = childService.findChild(parent.getId());
			
			map.put("parent", result);
			basicResponse.dataBody = map;
			basicResponse.data = "success";
			basicResponse.status = true;
			
		} catch(Exception e) {
			basicResponse.data = "error";
			basicResponse.status = false;
			e.printStackTrace();
		} finally {
			return new ResponseEntity<>(basicResponse, HttpStatus.OK);
		}
	}
	*/
	
}
