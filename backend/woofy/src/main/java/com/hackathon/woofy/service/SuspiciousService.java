package com.hackathon.woofy.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hackathon.woofy.entity.Suspicious;
import com.hackathon.woofy.repo.SuspiciousRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SuspiciousService {

	private final SuspiciousRepo suspiciousRepo;
	
	@Transactional
	public void saveSuspicious(Suspicious suspicious) {
		suspiciousRepo.save(suspicious);
	}
	
}
