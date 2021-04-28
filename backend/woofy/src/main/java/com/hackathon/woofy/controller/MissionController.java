package com.hackathon.woofy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
@RequestMapping("/api/v1/mission")
public class MissionController {

	private final ParentService parentService;
	private final MissionService missionService;
	private final ChildService childService;


	/**
	 * 부모가 모든 자식의 미션 상황을 조회
	 * @param missionRequest
	 * @return
	 */
	@GetMapping("{p_username}/childs")
	public Object findByParent(@PathVariable(name = "p_username") String p_username) {
		
		final BasicResponse basicResponse = new BasicResponse();

		try {
			Map<String, Object> map = new HashMap<>();
			
			Parent p = parentService.findParent(p_username);
			
			List<Mission> result = missionService.findByParent(p);

			map.put("mission", result);
			
			if(result.size() != 0) {
				basicResponse.dataBody = map;
				basicResponse.data = "success";
				basicResponse.status = true;
				
			} else {
				basicResponse.dataBody = map;
				basicResponse.data = "none";
				basicResponse.status = true;
			}

		} catch (Exception e) {
			basicResponse.data = "error";
			basicResponse.status = false;
			e.printStackTrace();
		} finally {
			return new ResponseEntity<>(basicResponse, HttpStatus.OK);
		}
	}
	
	/**
	 * 부모가 한 자식의 미션 상황을 조회
	 * @param parent
	 * @param child
	 * @return
	 */
	@GetMapping("{c_username}")
	public Object findByParentAndChild(@PathVariable(name = "c_username") String c_username) {
		final BasicResponse basicResponse = new BasicResponse();

		try {
			Map<String, Object> map = new HashMap<>();
			System.out.println(basicResponse.toString());
			
			Child c = childService.findByUsername(c_username);
			
			List<Mission> result = missionService.findByParentAndChild(c.getParent(), c);

			for(Mission m : result) {
				System.out.println(m);
			}
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
	
	/**
	 * 부모가 자식의 미션을 저장
	 * @param missionRequest
	 * @return
	 */
	@PostMapping()
	public Object saveMission(@RequestBody MissionRequest missionRequest) {
		final BasicResponse basicResponse = new BasicResponse();

		try {
			Map<String, Object> map = new HashMap<>();

			Mission result = new Mission(missionRequest);
			missionService.saveMission(result);

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
	
	/**
	 * 부모가 자식의 미션 수정
	 * @param missionRequest
	 * @return
	 */
	@PutMapping("{mission_id}")
	public Object updateMission(@PathVariable(name = "mission_id") Long mission_id, 
			@RequestBody MissionRequest missionRequest) {
		final BasicResponse basicResponse = new BasicResponse();

		try {
			Map<String, Object> map = new HashMap<>();
			
			Mission result = missionService.findById(mission_id);
			result.setTitle(missionRequest.getTitle());
			result.setContent(missionRequest.getContent());
			result.setPrize(missionRequest.getPrize());
			result.setMissionStatus(missionRequest.getMissionStatus());

			missionService.saveMission(result);
			
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
	
	@DeleteMapping("{mission_id}")
	public Object updateMission(@PathVariable(name = "mission_id") Long mission_id) {
		final BasicResponse basicResponse = new BasicResponse();

		try {
			Map<String, Object> map = new HashMap<>();
			
			missionService.deleteMission(mission_id);
			
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
	
	/*
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
	 */

}
