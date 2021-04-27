package com.hackathon.woofy.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hackathon.woofy.entity.Child;
import com.hackathon.woofy.entity.Mission;
import com.hackathon.woofy.entity.Parent;
import com.hackathon.woofy.request.MissionRequest;
import com.hackathon.woofy.service.ChildService;
import com.hackathon.woofy.service.MissionService;
import com.hackathon.woofy.util.BasicResponse;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/mission")
public class MissionController {

	private final MissionService missionService;
	private final ChildService childService;
	
	@PostMapping("/add")
	public Object add(@RequestBody MissionRequest missionRequest) {
		final BasicResponse basicResponse = new BasicResponse();
		
		try {
			Map<String, Object> map = new HashMap<>();
			
			Mission result = new Mission(missionRequest);
			missionService.addMission(result);
			
			map.put("mission", result);
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
