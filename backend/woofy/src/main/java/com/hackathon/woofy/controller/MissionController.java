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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hackathon.woofy.entity.Child;
import com.hackathon.woofy.entity.Mission;
import com.hackathon.woofy.entity.Parent;
import com.hackathon.woofy.request.MissionRequest;
import com.hackathon.woofy.response.BasicResponse;
import com.hackathon.woofy.service.ChildService;
import com.hackathon.woofy.service.MissionService;
import com.hackathon.woofy.service.ParentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/missions")
public class MissionController {

	private final MissionService missionService;
	private final ParentService parentService;
	private final ChildService childService;
	
	@PostMapping(value = "", produces = "application/json; charset=utf8")
	public Object add(@RequestBody Map<String, Object> jsonRequest) {
		
		final BasicResponse basicResponse = new BasicResponse();
		
		Map<String, Object> missionRequestObject = (Map<String, Object>) jsonRequest.get("dataBody");
		Parent parent = parentService.findParent("01012341234");
		Child child = childService.findChild((String)missionRequestObject.get("childUsername"));
		
		try {
			Map<String, Object> map = new HashMap<>();
			
			Mission result = new Mission(missionRequestObject, parent, child);
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
	
	@GetMapping(value = "/{missionId}")
	public String getMissionInfo(@PathVariable(value="missionId") Long missionId) {
		System.out.println(missionId);
		return "DEBUG";
	}
	
	@PostMapping(value = "/{missionId}", produces = "application/json; charset=utf8")
	public String updateMissionStatus(@PathVariable(value="missionId") Long missionId, @RequestBody Map<String, Object> jsonRequest) {
		Map<String, Object> missionRequestObject = (Map<String, Object>) jsonRequest.get("dataBody");
		System.out.println(missionId);
		System.out.println(missionRequestObject);
		return "DEBUG";
	}
	
	@PutMapping(value = "/{missionId}", produces = "application/json; charset=utf8")
	public String putMissionInfo(@PathVariable(value="missionId") Long missionId, @RequestBody Map<String, Object> jsonRequest) {
		Map<String, Object> missionRequestObject = (Map<String, Object>) jsonRequest.get("dataBody");
		System.out.println(missionId);
		System.out.println(missionRequestObject);
		return "DEBUG";
	}
	
	@DeleteMapping(value = "/{missionId}")
	public String deleteMissionInfo(@PathVariable(value="missionId") Long missionId) {
		System.out.println(missionId);
		return "DEBUG";
	}
	
	
}
