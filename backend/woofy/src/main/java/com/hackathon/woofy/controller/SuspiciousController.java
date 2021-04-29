package com.hackathon.woofy.controller;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
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
import com.hackathon.woofy.entity.Suspicious;
import com.hackathon.woofy.request.SuspiciousRequest;
import com.hackathon.woofy.response.BasicResponse;
import com.hackathon.woofy.service.ChildService;
import com.hackathon.woofy.service.ParentService;
import com.hackathon.woofy.service.SuspiciousService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/suspicious")
public class SuspiciousController {

	private final SuspiciousService suspiciousService;
	private final ParentService parentService;
	private final ChildService childService;
	
	@PostMapping(value = "", produces = "application/json; charset=utf8")
	public Object add(@RequestBody Map<String, Object> jsonRequest) {
		final BasicResponse basicResponse = new BasicResponse();
		
		Map<String, Object> suspiciousRequestObject = (Map<String, Object>) jsonRequest.get("dataBody");
		
		Parent parent = parentService.findParent("p_username");	// implement here when spring security ready
		Child child = childService.findByUsername("c_username");
		
		try {
			Map<String, Object> map = new HashMap<>();
			
			Suspicious result = new Suspicious(suspiciousRequestObject, parent, child);
			suspiciousService.saveSuspicious(result);
			
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

	/**
	 * 자식의 의심정보 조회
	 * @param suspiciousId
	 * @return
	 */
	@GetMapping(value = "/{suspiciousId}")
	public String getMissionInfo(@PathVariable(value="suspiciousId") Long suspiciousId) {
		
		System.out.println(suspiciousId);
		return "DEBUG";
	}

	@PutMapping(value = "/{suspiciousId}", produces = "application/json; charset=utf8")
	public String putMissionInfo(@PathVariable(value="suspiciousId") Long suspiciousId, @RequestBody Map<String, Object> jsonRequest) {
		Map<String, Object> missionRequestObject = (Map<String, Object>) jsonRequest.get("dataBody");
		System.out.println(suspiciousId);
		System.out.println(missionRequestObject);
		return "DEBUG";
	}
	
	@DeleteMapping(value = "/{suspiciousId}")
	public String deleteMissionInfo(@PathVariable(value="suspiciousId") Long suspiciousId) {
		System.out.println(suspiciousId);
		return "DEBUG";
	}
}
