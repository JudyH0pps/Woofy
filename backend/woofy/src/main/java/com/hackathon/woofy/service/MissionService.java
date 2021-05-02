package com.hackathon.woofy.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hackathon.woofy.entity.Child;
import com.hackathon.woofy.entity.Mission;
import com.hackathon.woofy.entity.Parent;
import com.hackathon.woofy.repo.MissionRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MissionService {

	private final MissionRepo missionRepo;
	
	@Transactional
	public Mission findById(Long id) {
		return missionRepo.findById(id).get(); // 단건 조회
	}
	
	/**
	 * 미션 저장
	 * @param mission
	 */
	@Transactional
	public Mission saveMission(Mission mission) {
		return missionRepo.save(mission);
	}
	
	/**
	 * 미션 삭제
	 * @param id
	 */
	@Transactional
	public void deleteMission(Long id) {
		missionRepo.deleteById(id);
	}
	
	/**
	 * 부모가 설정한 미션 전체조회
	 * @param parent
	 * @return
	 */
	@Transactional
	public List<Mission> findByParent(Parent parent) {
		return missionRepo.findByParent(parent);
	}
	
	@Transactional
	public List<Mission> findByChild(Child child) {
		return missionRepo.findByChild(child);
	}
	
	/**
	 * 자식(1명)의 미션 조회
	 * @param parent
	 * @param child
	 * @return
	 */
	@Transactional
	public List<Mission> findByParentAndChild(Parent parent, Child child){
		return missionRepo.findByParentAndChild(parent, child);
	}

}
