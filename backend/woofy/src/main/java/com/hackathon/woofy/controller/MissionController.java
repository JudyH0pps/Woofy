package com.hackathon.woofy.controller;

import java.util.ArrayList;
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
import com.hackathon.woofy.entity.MissionDetail;
import com.hackathon.woofy.entity.Parent;
import com.hackathon.woofy.request.MissionRequest;
import com.hackathon.woofy.response.BasicResponse;
import com.hackathon.woofy.service.ChildService;
import com.hackathon.woofy.service.MissionDetailService;
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
	private final MissionDetailService missionDetailService;
	private final ChildService childService;
	private final UserService userService;

	@Secured({ "ROLE_PARENT", "ROLE_CHILD" })
	@PostMapping(value = "", produces = "application/json; charset=utf8")
	public Object addMissionByParent(@RequestBody MissionRequest missionRequest) {

		final BasicResponse basicResponse = new BasicResponse();

		Authentication authUser = SecurityContextHolder.getContext().getAuthentication();
		Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) authUser.getAuthorities();

		String currentAuthority = null;

		for (SimpleGrantedAuthority permission : authorities) {
			currentAuthority = permission.getAuthority();
			break;
		}

		if (currentAuthority.equals("ROLE_PARENT")) { // 부모가 미션을 등록하는 경우 '매일미션'/'자식미션' 등록 가능
			Parent targetRequestedParent = parentService.findByUsername(authUser.getName());

			Mission mission = missionRequest.getMission();
			List<MissionDetail> missionDetails = missionRequest.getMissiondetailList();

			if (missionRequest.getMission().getIsDaily() == 1) { // '매일 미션'을 등록하는 경우

				try {
					List<Child> childs = childService.findByParent(targetRequestedParent);

					for (Child c : childs) {
						mission.setChild(c);
						missionService.saveMission(mission);

						for (MissionDetail md : missionDetails) {
							md.setMission(mission);

							missionDetailService.saveMissionDetail(md);
						}
					}

					basicResponse.status = "success";

				} catch (Exception e) {
					basicResponse.status = "error";
					e.printStackTrace();
					return new ResponseEntity<>(basicResponse, HttpStatus.BAD_REQUEST);
				}

			} else { // 자식 각각에게 미션을 줄 경우

				try {
					Child child = childService.findByUsername(missionRequest.getChildUsername()); // 선택한 자식

					mission.setChild(child);
					missionService.saveMission(mission);

					for (MissionDetail md : missionDetails) {
						md.setMission(mission);

						missionDetailService.saveMissionDetail(md);
					}

					basicResponse.status = "success";

				} catch (Exception e) {
					basicResponse.status = "error";
					e.printStackTrace();
					return new ResponseEntity<>(basicResponse, HttpStatus.BAD_REQUEST);
				}
			}
		} else { // 자식이 부모에게 미션을 승인을 요청하는 경우
			
			try {
				
				Child targetRequestedChild = childService.findByUsername(authUser.getName()); 
				
				Mission mission = missionRequest.getMission();
				mission.setChild(targetRequestedChild);
				missionService.saveMission(mission); // DB에 저장은 하되, isRequest == 1
				
				List<MissionDetail> missionDetails = missionRequest.getMissiondetailList();
				
				for (MissionDetail md : missionDetails) {
					md.setMission(mission);

					missionDetailService.saveMissionDetail(md);
				}

				basicResponse.status = "success";

			} catch (Exception e) {
				basicResponse.status = "error";
				e.printStackTrace();
				return new ResponseEntity<>(basicResponse, HttpStatus.BAD_REQUEST);
			}
			
		}

		return new ResponseEntity<>(basicResponse, HttpStatus.OK);
	}
	
	/** 
	 * 부모: 자식들의 미션 확인하기
	 * @param missionRequest
	 * @return
	 */
	@Secured({ "ROLE_PARENT" })
	@GetMapping(value = "/parent", produces = "application/json; charset=utf8")
	public Object findMissionByParent() {

		final BasicResponse basicResponse = new BasicResponse();

		Authentication authUser = SecurityContextHolder.getContext().getAuthentication();
		Parent targetRequestedParent = parentService.findByUsername(authUser.getName());

		Map<String, Object> map = new HashMap<>();

		try {
			List<Child> childs = childService.findByParent(targetRequestedParent);

			if(childs == null) { // 자식이 없을 경우 none
				basicResponse.status = "none";
			} else {
				List<Mission> result = null;
				
				for(Child c : childs) {
					map.put(c.getUsername() , missionService.findByChild(c));
				}
				
				basicResponse.dataBody = map;
				basicResponse.status = "success";
			}

		} catch (Exception e) {
			basicResponse.status = "error";
			e.printStackTrace();
			return new ResponseEntity<>(basicResponse, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(basicResponse, HttpStatus.OK);
	}
	
	/** 
	 * 자식: 자신의 미션 확인하기
	 * @param missionRequest
	 * @return
	 */
	@Secured({ "ROLE_CHILD" })
	@GetMapping(value = "/child", produces = "application/json; charset=utf8")
	public Object findMissionByChild() {

		final BasicResponse basicResponse = new BasicResponse();

		Authentication authUser = SecurityContextHolder.getContext().getAuthentication();
		Child targetRequestedChild = childService.findByUsername(authUser.getName());

		Map<String, Object> map = new HashMap<>();

		try {
			
			List<Mission> result = missionService.findByChild(targetRequestedChild);
			
			if(result == null) {
				basicResponse.status = "none";
			} else {
				
				basicResponse.dataBody = result;
				basicResponse.status = "success";
			}

		} catch (Exception e) {
			basicResponse.status = "error";
			e.printStackTrace();
			return new ResponseEntity<>(basicResponse, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(basicResponse, HttpStatus.OK);
	}
	
	/**
	 * 자식들이 요청한 모든 미션 조회
	 * @return
	 */
	@Secured({ "ROLE_PARENT" })
	@GetMapping(value = "/request", produces = "application/json; charset=utf8")
	public Object findRequestMission() {
		final BasicResponse basicResponse = new BasicResponse();

		Authentication authUser = SecurityContextHolder.getContext().getAuthentication();
		Parent targetRequestedParent = parentService.findByUsername(authUser.getName());

		Map<String, Object> map = new HashMap<>();

		try {
			List<Child> childs = childService.findByParent(targetRequestedParent);

			if(childs == null) { // 자식이 없을 경우 none
				basicResponse.status = "none";
			} else {
				
				
				for(Child c : childs) {
					List<Mission> result = new ArrayList<>();;
					List<Mission> missions = missionService.findByChild(c);
					
					for(Mission m : missions) {
						if(m.getIsRequest() == 1) {
							result.add(m);
						}
					}
					map.put(c.getUsername(), result);
				}
				
				basicResponse.dataBody = map;
				basicResponse.status = "success";
			}

		} catch (Exception e) {
			basicResponse.status = "error";
			e.printStackTrace();
			return new ResponseEntity<>(basicResponse, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(basicResponse, HttpStatus.OK);
	}
	
	@Secured({ "ROLE_PARENT" })
	@PutMapping(value = "/approval/{mission_id}", produces = "application/json; charset=utf8")
	public Object approvalRequest(@PathVariable(name = "mission_id") Long mission_id) {
		final BasicResponse basicResponse = new BasicResponse();

		try {
			Mission mission = missionService.findById(mission_id);
			mission.setIsRequest(0);
			missionService.saveMission(mission);
			
			basicResponse.status = "success";

		} catch (Exception e) {
			basicResponse.status = "error";
			e.printStackTrace();
			return new ResponseEntity<>(basicResponse, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(basicResponse, HttpStatus.OK);
	}
	
	@Secured({ "ROLE_PARENT" })
	@DeleteMapping(value = "/{mission_id}", produces = "application/json; charset=utf8")
	public Object removeMission(@PathVariable(name = "mission_id") Long mission_id) {
		final BasicResponse basicResponse = new BasicResponse();

		try {
			Mission mission = missionService.findById(mission_id);
			missionDetailService.deleteMissionDetail(mission);
			missionService.deleteMission(mission_id);
			
			basicResponse.status = "success";

		} catch (Exception e) {
			basicResponse.status = "error";
			e.printStackTrace();
			return new ResponseEntity<>(basicResponse, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(basicResponse, HttpStatus.OK);
	}
	

}
