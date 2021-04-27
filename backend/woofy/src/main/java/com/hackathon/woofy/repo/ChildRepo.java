package com.hackathon.woofy.repo;

import org.springframework.data.repository.CrudRepository;

import com.hackathon.woofy.entity.Child;

public interface ChildRepo extends CrudRepository<Child, Long>{

	Child findByPhoneNumber(String phoneNumber);
	
}
