package com.hackathon.woofy.controller;

import java.util.HashMap;
import java.util.List;
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
import com.hackathon.woofy.entity.Mission;
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
	
	/**
	 * 부모가 자식의 의심 데이터 저장
	 * @param suspiciousRequest
	 * @return
	 */
	@PostMapping(value = "", produces = "application/json; charset=utf8")
	public Object add(@RequestBody SuspiciousRequest suspiciousRequest) {
		final BasicResponse basicResponse = new BasicResponse();
		
//		Map<String, Object> suspiciousRequestObject = (Map<String, Object>) jsonRequest.get("dataBody");
//		Parent parent = parentService.findParent("p_username");	// implement here when spring security ready
//		Child child = childService.findByUsername("c_username");
		
		try {
			Map<String, Object> map = new HashMap<>();
			
			Parent p = parentService.findById(suspiciousRequest.getParent().getId());
			Child c = childService.findById(suspiciousRequest.getChild().getId());
			
			Suspicious result = new Suspicious(suspiciousRequest);
			result.setParent(p);
			result.setChild(c);
			
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
	 * 의심 데이터 Id로 단건 조회
	 * @param suspicious_id
	 * @return
	 */
	@GetMapping(value = "/{suspicious_id}", produces = "application/json; charset=utf8")
	public Object findByMissionId(@PathVariable(name = "suspicious_id") Long suspicious_id) {
		final BasicResponse basicResponse = new BasicResponse();

		try {
			Map<String, Object> map = new HashMap<>();

			Suspicious result = suspiciousService.findById(suspicious_id);
			
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

	@PutMapping(value = "/{suspicious_id}", produces = "application/json; charset=utf8")
	public Object updateSuspicious(@PathVariable(name = "suspicious_id") Long suspicious_id, 
			@RequestBody SuspiciousRequest suspiciousRequest) {
		final BasicResponse basicResponse = new BasicResponse();

		try {
			Map<String, Object> map = new HashMap<>();
			
			Suspicious result = suspiciousService.findById(suspicious_id);
			result.setLocation(suspiciousRequest.getLocation());
			result.setStartTime(suspiciousRequest.getStartTime());
			result.setEndTime(suspiciousRequest.getEndTime());

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
	
	@DeleteMapping(value = "/{suspicious_id}", produces = "application/json; charset=utf8")
	public Object updateMission(@PathVariable(name = "suspicious_id") Long suspicious_id) {
		final BasicResponse basicResponse = new BasicResponse();

		try {
			Map<String, Object> map = new HashMap<>();
			
			suspiciousService.deleteSuspicious(suspicious_id);
			
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
	 * 부모가 자식의 의심정보 전체 조회
	 * @param p_username
	 * @return
	 */
	@GetMapping(value = "/{p_username}/childs", produces = "application/json; charset=utf8")
	public Object findByParent(@PathVariable(name = "p_username") String p_username) {
		
		final BasicResponse basicResponse = new BasicResponse();

		try {
			Map<String, Object> map = new HashMap<>();
			
			Parent p = parentService.findParent(p_username);
			
			List<Suspicious> result = suspiciousService.findByParent(p);
			
			map.put("suspicious", result);
			
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
	 * 부모가 한 자식의 의심 데이터 조회
	 * @param c_username
	 * @return
	 */
	@GetMapping(value = "/{c_username}/child", produces = "application/json; charset=utf8")
	public Object findByParentAndChild(@PathVariable(name = "c_username") String c_username) {
		final BasicResponse basicResponse = new BasicResponse();

		try {
			Map<String, Object> map = new HashMap<>();
			
			Child c = childService.findByUsername(c_username);
			
			List<Suspicious> result = suspiciousService.findByParentAndChild(c.getParent(), c);

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
}
