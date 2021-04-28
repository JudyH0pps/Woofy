package com.hackathon.woofy.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hackathon.woofy.entity.Parent;
import com.hackathon.woofy.request.UserRequest;
import com.hackathon.woofy.response.ApiResponse;
import com.hackathon.woofy.response.BasicResponse;
import com.hackathon.woofy.service.ParentService;

@RestController
@RequestMapping("/api/v1/parent")
public class ParentController {
	
	@Autowired ParentService parentService;

	@PostMapping(value = "", produces = "application/json; charset=utf8")
	public Object signup(@RequestBody Map<String, Object> jsonRequest) {

		final BasicResponse basicResponse = new BasicResponse();
		
		Map<String, Object> parentObject = (Map<String, Object>) jsonRequest.get("dataBody");
		
		try {
			Map<String, Object> map = new HashMap<>();
			
			Parent parent = new Parent(parentObject);						
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
	
	@PutMapping(value = "/{parentUsername}", produces = "application/json; charset=utf8")
	public String modifyParentInfo(@PathVariable(value="parentUsername") String parentUserName, @RequestBody Map<String, Object> jsonRequest) {
//		JsonObject dataBody = JsonParser.parseString(jsonRequest.toString()).getAsJsonObject();
		Map<String, Object> parentObject = (Map<String, Object>) jsonRequest.get("dataBody");
		System.out.println(parentObject);
		
		return "DEBUG";
	}
	
}
