package com.hackathon.woofy.service;

import java.util.List;
import java.util.Optional;

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
		return missionRepo.findById(id).get(); // �ܰ� ��ȸ
	}
	
	/**
	 * �θ� ��� �ڽ��� �̼� ��Ȳ�� ��ȸ
	 * @param parent
	 * @return
	 */
	@Transactional
	public List<Mission> findByParent(Parent parent) {
		return missionRepo.findByParent(parent);
	}
	
	/**
	 * �θ� �� �ڽ��� �̼� ��Ȳ�� ��ȸ
	 * @param parent
	 * @param child
	 * @return
	 */
	@Transactional
	public List<Mission> findByParentAndChild(Parent parent, Child child){
		return missionRepo.findByParentAndChild(parent, child);
	}
	
	/**
	 * �θ� �ڽ��� �̼��� ����
	 * @param mission
	 */
	@Transactional
	public void saveMission(Mission mission) {
		missionRepo.save(mission);
	}
	
	@Transactional
	public void deleteMission(Long id) {
		missionRepo.deleteById(id);
	}
}
