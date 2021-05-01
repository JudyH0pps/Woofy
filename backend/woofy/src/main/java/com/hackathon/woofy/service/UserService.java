package com.hackathon.woofy.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hackathon.woofy.entity.User;
import com.hackathon.woofy.repo.UserRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepo userRepo;
	
	@Transactional
	public User findByUsername(String username) {
		return userRepo.findByUsername(username);
	}
  
	@Transactional
	public User findByPhoneNumber(String phoneNumber) {
		return userRepo.findByPhoneNumber(phoneNumber);
	}
	
	@Transactional
	public User saveUser(User user) {
		return userRepo.save(user);
	}
}
