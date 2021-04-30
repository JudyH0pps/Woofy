package com.hackathon.woofy.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hackathon.woofy.entity.Child;
import com.hackathon.woofy.entity.Mission;
import com.hackathon.woofy.entity.Parent;
import com.hackathon.woofy.entity.Transinfo;
import com.hackathon.woofy.entity.TransinfoStatus;
import com.hackathon.woofy.request.TransinfoRequest;
import com.hackathon.woofy.response.BasicResponse;
import com.hackathon.woofy.service.ChildService;
import com.hackathon.woofy.service.ParentService;
import com.hackathon.woofy.service.TransinfoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment")
public class TransinfoController {

	private final TransinfoService transinfoService;
	private final ParentService parentService;
	private final ChildService childService;

	/**
	 * 부모는 자신의 모든 거래내역(자식 포함)을 조회
	 * @param p_username
	 * @return
	 */
	/*
	@GetMapping(value = "/{p_username}/childs", produces = "application/json; charset=utf8")
	public Object findByParent(@PathVariable(name = "p_username") String p_username) {

		final BasicResponse basicResponse = new BasicResponse();

		try {
			Map<String, Object> map = new HashMap<>();

			Parent p = parentService.findParent(p_username);

			List<Mission> result = missionService.findByParent(p);

			map.put("mission", result);

			if (result.size() != 0) {
				basicResponse.dataBody = map;
				basicResponse.status = "200";

			} else {
				basicResponse.dataBody = map;
				basicResponse.status = "200 none";
			}

		} catch (Exception e) {
			basicResponse.status = "400";
			e.printStackTrace();
		} finally {
			return new ResponseEntity<>(basicResponse, HttpStatus.OK);
		}
	}
	*/
}
