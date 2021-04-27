package com.hackathon.woofy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hackathon.woofy.entity.Child;
import com.hackathon.woofy.repo.ChildRepo;

@Service
public class ChildService {

	@Autowired ChildRepo childRepo;
	
	public Child saveChild(Child child) {
		return childRepo.save(child);
	}
}
