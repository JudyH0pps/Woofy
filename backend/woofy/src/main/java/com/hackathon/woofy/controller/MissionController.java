package com.hackathon.woofy.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import com.hackathon.woofy.entity.User;
import com.hackathon.woofy.request.MissionRequest;
import com.hackathon.woofy.response.BasicResponse;
import com.hackathon.woofy.service.ChildService;
import com.hackathon.woofy.service.MissionService;
import com.hackathon.woofy.service.ParentService;
import com.hackathon.woofy.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/missions")
public class MissionController {

	private final ParentService parentService;
	private final MissionService missionService;
	private final ChildService childService;
	private final UserService userService;

	/**
	 * 부모가 자식의 미션을 저장
	 * @param missionRequest
	 * @return
	 */
	@Secured("ROLE_PARENT")
	@PostMapping(value="", produces = "application/json; charset=utf8")
	public Object saveMission(@RequestBody MissionRequest missionRequest) {
		final BasicResponse basicResponse = new BasicResponse();
		
		Authentication authUser = SecurityContextHolder.getContext().getAuthentication();
		Parent targetRequestedParent = parentService.findByUsername(authUser.getName());
				
		try {
			Map<String, Object> map = new HashMap<>();
			
			Child targetChild = childService.findByUsername(missionRequest.getChildUsername());
			
			if (targetChild == null || targetChild.getParent() != targetRequestedParent) {
				basicResponse.status = "error";
				return new ResponseEntity<>(basicResponse, HttpStatus.BAD_REQUEST);
			}
			
			Mission result = new Mission(missionRequest);
			result.setChild(targetChild);
			
			missionService.saveMission(result);
			
			// JSON Response 객체 생성 및 구성
			basicResponse.status = "success";
			map.put("mission", result);
			basicResponse.dataBody = map;

		} catch (Exception e) {
			basicResponse.status = "error";
			e.printStackTrace();
			return new ResponseEntity<>(basicResponse, HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(basicResponse, HttpStatus.OK);
	}

	/**
	 * 미션Id로 단건 조회
	 * @param mission_id
	 * @return
	 */
	@Secured({"ROLE_PARENT", "ROLE_CHILD"})
	@GetMapping(value = "/{mission_id}", produces = "application/json; charset=utf8")
	public Object findByMissionId(@PathVariable(name = "mission_id") Long mission_id) {
		final BasicResponse basicResponse = new BasicResponse();

//		Authentication authUser = SecurityContextHolder.getContext().getAuthentication();
//		User targetRequestedUser = userService.findByUsername(authUser.getName());
		
		try {
			Map<String, Object> map = new HashMap<>();
			Mission result = missionService.findById(mission_id);
			
			map.put("mission", result);
			basicResponse.dataBody = map;
			basicResponse.status = "success";
		} catch (Exception e) {
			basicResponse.status = "error";
			e.printStackTrace();
			return new ResponseEntity<>(basicResponse, HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(basicResponse, HttpStatus.OK);
	}
	
	@Secured("ROLE_PARENT")
	@PutMapping(value = "/{mission_id}", produces = "application/json; charset=utf8")
	public Object updateMission(@PathVariable(name = "mission_id") Long mission_id, @RequestBody MissionRequest missionRequest) {
		final BasicResponse basicResponse = new BasicResponse();
		
		Authentication authUser = SecurityContextHolder.getContext().getAuthentication();
		Parent targetRequestedParent = parentService.findByUsername(authUser.getName());

		try {
			Map<String, Object> map = new HashMap<>();
			
			Mission mission = missionService.findById(mission_id);
			
			// 수정을 요청한 부모가 소속된 부모가 아닌 경우는 이 요청이 유효하지 않다.
			if (mission == null || mission.getChild().getParent() != targetRequestedParent) {
				basicResponse.status = "error";
				return new ResponseEntity<>(basicResponse, HttpStatus.BAD_REQUEST);
			}
			
			mission.setTitle(missionRequest.getTitle());
			mission.setContent(missionRequest.getContent());
			mission.setPrize(missionRequest.getPrize());
			mission.setMissionStatus(missionRequest.getMissionStatus());

			missionService.saveMission(mission);
			
			map.put("mission", mission);
			basicResponse.dataBody = map;
			basicResponse.status = "success";

		} catch (Exception e) {
			basicResponse.status = "error";
			e.printStackTrace();
			return new ResponseEntity<>(basicResponse, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(basicResponse, HttpStatus.OK);
	}
	
	@Secured("ROLE_PARENT")
	@DeleteMapping(value = "/{mission_id}", produces = "application/json; charset=utf8")
	public Object deleteMission(@PathVariable(name = "mission_id") Long mission_id) {
		final BasicResponse basicResponse = new BasicResponse();

		Authentication authUser = SecurityContextHolder.getContext().getAuthentication();
		Parent targetRequestedParent = parentService.findByUsername(authUser.getName());
		
		try {
			Mission mission = missionService.findById(mission_id);
			
			// 수정을 요청한 부모가 소속된 부모가 아닌 경우는 이 요청이 유효하지 않다.
			if (mission == null || mission.getChild().getParent() != targetRequestedParent) {
				basicResponse.status = "error";
				return new ResponseEntity<>(basicResponse, HttpStatus.BAD_REQUEST);
			}
			
			missionService.deleteMission(mission_id);
			basicResponse.status = "success";

		} catch (Exception e) {
			basicResponse.status = "error";
			e.printStackTrace();
			return new ResponseEntity<>(basicResponse, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(basicResponse, HttpStatus.OK);
	}
	
	/**
	 * 부모가 모든 자식의 미션 상황을 조회
	 * @param missionRequest
	 * @return
	 */
	// 경고: 한 경로에 대한 여러 권한의 로직을 각기 다른 클래스 함수로 분할하는 것은 불가능하다. 
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
				
				List<Mission> result = missionService.findByParent(targetRequestedParent);
	
				map.put("mission", result);
				
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
				return new ResponseEntity<>(basicResponse, HttpStatus.BAD_REQUEST);
			}
		}
		
		else {
			Child targetRequestedChild = childService.findByUsername(authUser.getName());
			
			try {
				Map<String, Object> map = new HashMap<>();
								
				List<Mission> result = missionService.findByChild(targetRequestedChild);
	
				map.put("mission", result);
				
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
	 * 부모 혹은 자녀가 한 자녀의 미션 조회
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
							
			List<Mission> result = missionService.findByChild(targetRequestedChild);

			map.put("mission", result);
			
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
			return new ResponseEntity<>(basicResponse, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(basicResponse, HttpStatus.OK);
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
