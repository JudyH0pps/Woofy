package com.hackathon.woofy.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hackathon.woofy.entity.Child;

public interface ChildRepo extends JpaRepository<Child, Long>{

	
}
