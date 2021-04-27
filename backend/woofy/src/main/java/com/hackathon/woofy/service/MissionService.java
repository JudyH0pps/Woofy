package com.hackathon.woofy.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hackathon.woofy.entity.Mission;
import com.hackathon.woofy.repo.MissionRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MissionService {

	private final MissionRepo missionRepo;
	
	@Transactional
	public void addMission(Mission mission) {
		missionRepo.save(mission);
	}
	
}
