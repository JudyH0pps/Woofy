package com.hackathon.woofy.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hackathon.woofy.entity.Child;
import com.hackathon.woofy.entity.Parent;
import com.hackathon.woofy.repo.ChildRepo;
import com.hackathon.woofy.repo.ParentRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChildService {

	private final ChildRepo childRepo;
	
	@Transactional
	public Child saveChild(Child child) {
		
		return childRepo.save(child);
	}
	
	public Child findChild(String phoneNumber) {
		return childRepo.findByPhoneNumber(phoneNumber);
	}
}
