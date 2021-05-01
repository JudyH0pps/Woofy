package com.hackathon.woofy.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hackathon.woofy.entity.Mission;
import com.hackathon.woofy.entity.MissionDetail;
import com.hackathon.woofy.repo.MissionDetailRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MissionDetailService {

	private final MissionDetailRepo missionDetailRepo;
	
	@Transactional
	public void saveMissionDetail(MissionDetail missionDetail) {
		missionDetailRepo.save(missionDetail);
	}
	
	@Transactional
	public List<MissionDetail> findByMission(Mission mission){
		return missionDetailRepo.findByMission(mission);
	}
	
	@Transactional
	public MissionDetail findById(Long missiondetail_id) {
		return missionDetailRepo.findById(missiondetail_id).get();
	}
	
	
}
