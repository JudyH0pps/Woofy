package com.hackathon.woofy.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hackathon.woofy.entity.Child;
import com.hackathon.woofy.entity.Parent;
import com.hackathon.woofy.request.UserRequest;
import com.hackathon.woofy.response.BasicResponse;
import com.hackathon.woofy.service.ChildService;
import com.hackathon.woofy.service.ParentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/child")
public class ChildController {

	private final ChildService childService;
	private final ParentService parentService;

	@PostMapping(value = "", produces = "application/json; charset=utf8")
	public Object signup(@RequestBody Map<String, Object> jsonRequest) {

		final BasicResponse basicResponse = new BasicResponse();

		Map<String, Object> childObject = (Map<String, Object>) jsonRequest.get("dataBody");
		
		try {
			Map<String, Object> map = new HashMap<>();
			
			Parent parent = parentService.findParent("01012341234");	// 현단계에서는 디버그 전화번호를 사용한다.
			
			Child child = new Child(childObject, parent);
			Child result = childService.saveChild(child);
			
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
	
	@GetMapping(value = "/{childUsername}")
	public String getChildInfo(@PathVariable(value="childUsername") String childUsername) {
		System.out.println(childUsername);
		return "DEBUG";
	}

	@PutMapping(value = "/{childUsername}", produces = "application/json; charset=utf8")
	public String modifyChildInfo(@PathVariable(value="childUsername") String childUsername, @RequestBody Map<String, Object> jsonRequest) {
		// JsonObject dataBody = JsonParser.parseString(jsonRequest.toString()).getAsJsonObject();
		Map<String, Object> childObject = (Map<String, Object>) jsonRequest.get("dataBody");
		System.out.println(childObject);
		
		return "DEBUG";
	}

	@DeleteMapping(value = "/{childUsername}")
	public String deleteChildInfo(@PathVariable(value="childUsername") String childUsername) {
		//	JsonObject dataBody = JsonParser.parseString(jsonRequest.toString()).getAsJsonObject();
		System.out.println(childUsername);
		
		return "DEBUG";
	}	

	@PutMapping(value = "/{childUsername}/allowence", produces = "application/json; charset=utf8")
	public String modifyChildAllowenceInfo(@PathVariable(value="childUsername") String childUsername, @RequestBody Map<String, Object> jsonRequest) {
		// JsonObject dataBody = JsonParser.parseString(jsonRequest.toString()).getAsJsonObject();
		Map<String, Object> childObject = (Map<String, Object>) jsonRequest.get("dataBody");
		System.out.println(childObject);
		
		return "DEBUG";
	}

	@PutMapping(value = "/{childUsername}/amount", produces = "application/json; charset=utf8")
	public String modifyChildAmountInfo(@PathVariable(value="childUsername") String childUsername, @RequestBody Map<String, Object> jsonRequest) {
		// JsonObject dataBody = JsonParser.parseString(jsonRequest.toString()).getAsJsonObject();
		Map<String, Object> childObject = (Map<String, Object>) jsonRequest.get("dataBody");
		System.out.println(childObject);
		
		return "DEBUG";
	}	
	
}