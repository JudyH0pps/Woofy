package com.hackathon.woofy.repo;

import org.springframework.data.repository.CrudRepository;

import com.hackathon.woofy.entity.Parent;

public interface ParentRepo extends CrudRepository<Parent, Long>{
	
	Parent findByPhoneNumber(String phoneNumber);
}
