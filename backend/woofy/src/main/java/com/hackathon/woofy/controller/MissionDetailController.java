package com.hackathon.woofy.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hackathon.woofy.entity.Mission;
import com.hackathon.woofy.entity.MissionDetail;
import com.hackathon.woofy.entity.MissionStatus;
import com.hackathon.woofy.request.MissionRequest;
import com.hackathon.woofy.response.BasicResponse;
import com.hackathon.woofy.service.ChildService;
import com.hackathon.woofy.service.MissionDetailService;
import com.hackathon.woofy.service.MissionService;
import com.hackathon.woofy.service.ParentService;
import com.hackathon.woofy.service.UserService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/missiondetail")
public class MissionDetailController {

	private final ParentService parentService;
	private final ChildService childService;
	private final UserService userService;
	private final MissionDetailService missionDetailService;
	private final MissionService missionService;
	
	/**
	 * 
	 * @return
	 */
	@GetMapping(value = "/{mission_id}", produces = "application/json; charset=utf8")
	public Object findMissionDetailByMission(@PathVariable(name = "mission_id") Long mission_id) {

		final BasicResponse basicResponse = new BasicResponse();

		try {
			Mission mission = missionService.findById(mission_id);
			
			List<MissionDetail> result = missionDetailService.findByMission(mission);
				
			basicResponse.dataBody = result;
			basicResponse.status = "success";

		} catch (Exception e) {
			basicResponse.status = "error";
			e.printStackTrace();
			return new ResponseEntity<>(basicResponse, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(basicResponse, HttpStatus.OK);
	}
	
	/**
	 * 자식이 부모에게 세부 미션 완료 요청한다.
	 * @param missiondetail_id
	 * @param missionRequest
	 * @return
	 */
	@Secured({ "ROLE_CHILD" })
	@PutMapping(value = "/request/{missiondetail_id}", produces = "application/json; charset=utf8")
	public Object requestMissionComplete(@PathVariable(name = "missiondetail_id") Long missiondetail_id, @RequestBody MissionRequest missionRequest) {
		final BasicResponse basicResponse = new BasicResponse();
		
		try {
			MissionDetail missionDetail = missionDetailService.findById(missiondetail_id);
			
			missionDetail.setImageUrl(missionRequest.getMissiondetail().getImageUrl());
			missionDetail.setMissionStatus(MissionStatus.REQUEST);
			
			missionDetailService.saveMissionDetail(missionDetail);
			
			basicResponse.status = "success";
		} catch (Exception e) {
			basicResponse.status = "error";
			e.printStackTrace();
		} finally {
			return new ResponseEntity<>(basicResponse, HttpStatus.OK);
		}
	}
	
	/**
	 * 부모가 자식의 미션 요청을 확인 후 성공/실패 응답
	 * @param missiondetail_id
	 * @param missionRequest
	 * @return
	 */
	@Secured({ "ROLE_PARENT" })
	@PutMapping(value = "/response/{missiondetail_id}", produces = "application/json; charset=utf8")
	public Object requestComplete(@PathVariable(name = "missiondetail_id") Long missiondetail_id, @RequestBody MissionRequest missionRequest) {
		final BasicResponse basicResponse = new BasicResponse();
		
		try {
			MissionDetail missionDetail = missionDetailService.findById(missiondetail_id);
			
			missionDetail.setMissionStatus(missionRequest.getMissiondetail().getMissionStatus()); // 클라이언트가 SUCCESS/FAIL 전송
			
			missionDetailService.saveMissionDetail(missionDetail);
			
			basicResponse.status = "success";
		} catch (Exception e) {
			basicResponse.status = "error";
			e.printStackTrace();
		} finally {
			return new ResponseEntity<>(basicResponse, HttpStatus.OK);
		}
	}
	
	
}
