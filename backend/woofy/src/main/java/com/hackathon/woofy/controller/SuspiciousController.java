package com.hackathon.woofy.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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
	 * 
	 * @param suspiciousRequest
	 * @return
	 */
	@Secured({"ROLE_PARENT"})
	@PostMapping(value = "", produces = "application/json; charset=utf8")
	public Object add(@RequestBody SuspiciousRequest suspiciousRequest) {
		final BasicResponse basicResponse = new BasicResponse();

		Authentication authUser = SecurityContextHolder.getContext().getAuthentication();
		Parent targetRequestedParent = parentService.findByUsername(authUser.getName());

		try {
			Map<String, Object> map = new HashMap<>();

			Child targetChild = childService.findByUsername(suspiciousRequest.getChildUsername());

			if (targetChild == null || targetChild.getParent() != targetRequestedParent) {
				basicResponse.status = "error";
				return new ResponseEntity<>(basicResponse, HttpStatus.BAD_REQUEST);
			}

			Suspicious result = new Suspicious(suspiciousRequest);
			result.setChild(targetChild);

			suspiciousService.saveSuspicious(result);

			map.put("suspicious", result);
			basicResponse.dataBody = map;
			basicResponse.status = "success";

		} catch (Exception e) {
			basicResponse.status = "error";
			e.printStackTrace();
		} finally {
			return new ResponseEntity<>(basicResponse, HttpStatus.OK);
		}
	}

	/**
	 * 의심 데이터 Id로 단건 조회
	 * 
	 * @param suspicious_id
	 * @return
	 */
	@Secured({ "ROLE_PARENT", "ROLE_CHILD" })
	@GetMapping(value = "/{suspicious_id}", produces = "application/json; charset=utf8")
	public Object findByMissionId(@PathVariable(name = "suspicious_id") Long suspicious_id) {
		final BasicResponse basicResponse = new BasicResponse();

		try {
			Map<String, Object> map = new HashMap<>();

			Suspicious result = suspiciousService.findById(suspicious_id);

			map.put("suspicious", result);
			basicResponse.dataBody = map;
			basicResponse.status = "success";

		} catch (Exception e) {
			basicResponse.status = "error";
			e.printStackTrace();
		} finally {
			return new ResponseEntity<>(basicResponse, HttpStatus.OK);
		}
	}

	@Secured("ROLE_PARENT")
	@PutMapping(value = "/{suspicious_id}", produces = "application/json; charset=utf8")
	public Object updateSuspicious(@PathVariable(name = "suspicious_id") Long suspicious_id,
			@RequestBody SuspiciousRequest suspiciousRequest) {
		final BasicResponse basicResponse = new BasicResponse();

		Authentication authUser = SecurityContextHolder.getContext().getAuthentication();
		Parent targetRequestedParent = parentService.findByUsername(authUser.getName());

		try {
			Map<String, Object> map = new HashMap<>();

			Suspicious suspicious = suspiciousService.findById(suspicious_id);

			// 수정을 요청한 부모가 소속된 부모가 아닌 경우는 이 요청이 유효하지 않다.
			if (suspicious == null || suspicious.getChild().getParent() != targetRequestedParent) {
				basicResponse.status = "error";
				return new ResponseEntity<>(basicResponse, HttpStatus.BAD_REQUEST);
			}

			suspicious.setLocation(suspiciousRequest.getLocation());
			suspicious.setStartTime(suspiciousRequest.getStartTime());
			suspicious.setEndTime(suspiciousRequest.getEndTime());

			suspiciousService.saveSuspicious(suspicious);

			map.put("suspicious", suspicious);
			basicResponse.dataBody = map;
			basicResponse.status = "success";

		} catch (Exception e) {
			basicResponse.status = "error";
			e.printStackTrace();
		} finally {
			return new ResponseEntity<>(basicResponse, HttpStatus.OK);
		}
	}

	@Secured("ROLE_PARENT")
	@DeleteMapping(value = "/{suspicious_id}", produces = "application/json; charset=utf8")
	public Object deleteSuspicious(@PathVariable(name = "suspicious_id") Long suspicious_id) {
		final BasicResponse basicResponse = new BasicResponse();

		Authentication authUser = SecurityContextHolder.getContext().getAuthentication();
		Parent targetRequestedParent = parentService.findByUsername(authUser.getName());

		try {
			Suspicious suspicious = suspiciousService.findById(suspicious_id);

			// 수정을 요청한 부모가 소속된 부모가 아닌 경우는 이 요청이 유효하지 않다.
			if (suspicious == null || suspicious.getChild().getParent() != targetRequestedParent) {
				basicResponse.status = "error";
				return new ResponseEntity<>(basicResponse, HttpStatus.BAD_REQUEST);
			}

			suspiciousService.deleteSuspicious(suspicious_id);
			basicResponse.status = "success";

		} catch (Exception e) {
			basicResponse.status = "error";
			e.printStackTrace();
		} finally {
			return new ResponseEntity<>(basicResponse, HttpStatus.OK);
		}
	}

	/**
	 * 부모가 자식의 의심정보 전체 조회
	 * 
	 * @param p_username
	 * @return
	 */
	@Secured({"ROLE_PARENT", "ROLE_CHILD"})
	@GetMapping(value = "", produces = "application/json; charset=utf8")
	public Object findByParent() {

		final BasicResponse basicResponse = new BasicResponse();
		
		Authentication authUser = SecurityContextHolder.getContext().getAuthentication();
		Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) authUser.getAuthorities();
		
		String currentAuthority = null;
		
		for (SimpleGrantedAuthority permission : authorities) {
			currentAuthority = permission.getAuthority();
			break;
		}
		
		if (currentAuthority.equals("ROLE_PARENT")) {
			Parent targetRequestedParent = parentService.findByUsername(authUser.getName());
			
			try {
				Map<String, Object> map = new HashMap<>();

				List<Suspicious> result = suspiciousService.findByParent(targetRequestedParent);

				map.put("suspicious", result);

				if (result.size() != 0) {
					basicResponse.dataBody = map;
					basicResponse.status = "success";

				} else {
					basicResponse.status = "none";
				}
			} catch (Exception e) {
				basicResponse.status = "error";
				e.printStackTrace();
			}
			
		} else {
			Child targetRequestedChild = childService.findByUsername(authUser.getName());
			
			try {
				Map<String, Object> map = new HashMap<>();
								
				List<Suspicious> result = suspiciousService.findByChild(targetRequestedChild);
	
				map.put("suspicious", result);
				
				if(result.size() != 0) {
					basicResponse.dataBody = map;
					basicResponse.status = "success";
					
				} else {
					basicResponse.dataBody = map;
					basicResponse.status = "none";
				}
	
			} catch (Exception e) {
				basicResponse.status = "error";
				e.printStackTrace();
			}
		}
		
		

		return new ResponseEntity<>(basicResponse, HttpStatus.OK);
	}

	/**
	 * 부모가 한 자식의 의심 데이터 조회
	 * 
	 * @param c_username
	 * @return
	 */
	@Secured({"ROLE_CHILD", "ROLE_PARENT"})
	@GetMapping(value = "/child/{c_username}", produces = "application/json; charset=utf8")
	public Object findByParentAndChild(@PathVariable(name = "c_username") String c_username) {
		final BasicResponse basicResponse = new BasicResponse();
		
		Child targetRequestedChild = childService.findByUsername(c_username);

		try {
			Map<String, Object> map = new HashMap<>();
			
			List<Suspicious> result = suspiciousService.findByChild(targetRequestedChild);

			map.put("suspicious", result);
			if(result.size() != 0) {
				basicResponse.dataBody = map;
				basicResponse.status = "success";
				
			} else {
				basicResponse.dataBody = map;
				basicResponse.status = "none";
			}

		} catch (Exception e) {
			basicResponse.status = "error";
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(basicResponse, HttpStatus.OK);
	}
}
