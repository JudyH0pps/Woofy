package com.hackathon.woofy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hackathon.woofy.entity.Child;
import com.hackathon.woofy.entity.Parent;
import com.hackathon.woofy.repo.ChildRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChildService {

	private final ChildRepo childRepo;
	
	@Transactional
	public Child saveChild(Child child) {
		return childRepo.save(child);
	}
	
	public Child findByUsername(String username) {
		return childRepo.findByUsername(username);
	}

	public Child findByPhoneNumber(String phoneNumber) {
		return childRepo.findByPhoneNumber(phoneNumber);
	}

	
}
