package com.hackathon.woofy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hackathon.woofy.entity.Child;
import com.hackathon.woofy.entity.Parent;
import com.hackathon.woofy.entity.User;
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
	
//  
//	@Transactional
//	public Child findByPhoneNumber(String phoneNumber) {
//		return childRepo.findByPhoneNumber(phoneNumber);
//	}
	
	@Transactional
	public List<Child> findByParent(Parent parent) {
		return childRepo.findByParent(parent);
	}

	@Transactional
	public Child findByUsername(String username) {
		return childRepo.findByUsername(username);
	}
	
	@Transactional
	public Child findByUser(User user) {
		return childRepo.findByUser(user);
	}

	@Transactional
	public Child findById(Long id) {
		return childRepo.findById(id).get();
	}
	
	@Transactional
	public void deleteChild(Long id) {
		childRepo.deleteById(id);
	}
}
