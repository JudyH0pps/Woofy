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
@RequestMapping("/api/v1/transinfo")
public class TransinfoController {

	private final TransinfoService transinfoService;
	private final ParentService parentService;
	private final ChildService childService;

	/**
	 * 부모 -> 부모/자식 거래내역 전체 조회
	 * @param p_username
	 * @return
	 */
	@GetMapping(value = "/parent/{p_username}", produces = "application/json; charset=utf8")
	public Object findByParent(@PathVariable(name = "p_username") String p_username) {

		final BasicResponse basicResponse = new BasicResponse();

		try {
			Map<String, Object> map = new HashMap<>();

			Parent p = parentService.findByUsername(p_username);

			List<Transinfo> result = transinfoService.findByParent(p);

			map.put("transinfo", result);

			if (result.size() != 0) {
				basicResponse.dataBody = map;
				basicResponse.status = "success";

			} else {
				basicResponse.dataBody = map;
				basicResponse.status = "none";
			}

		} catch (Exception e) {
			basicResponse.status = "error";
			e.printStackTrace();
		} finally {
			return new ResponseEntity<>(basicResponse, HttpStatus.OK);
		}
	}
	
	/**
	 * 자식 -> 자신의 거래내역 전체 조회
	 * @param c_username
	 * @return
	 */
	@GetMapping(value = "/child/{c_username}", produces = "application/json; charset=utf8")
	public Object findByChild(@PathVariable(name = "c_username") String c_username) {

		final BasicResponse basicResponse = new BasicResponse();

		try {
			Map<String, Object> map = new HashMap<>();
			
			Child c = childService.findByUsername(c_username);

			List<Transinfo> result = transinfoService.findByChild(c.getId());

			map.put("transinfo", result);

			if (result.size() != 0) {
				basicResponse.dataBody = map;
				basicResponse.status = "success";

			} else {
				basicResponse.dataBody = map;
				basicResponse.status = "none";
			}

		} catch (Exception e) {
			basicResponse.status = "error";
			e.printStackTrace();
		} finally {
			return new ResponseEntity<>(basicResponse, HttpStatus.OK);
		}
	}
	
}
