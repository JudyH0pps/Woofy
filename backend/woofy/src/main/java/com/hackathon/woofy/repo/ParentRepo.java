package com.hackathon.woofy.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.hackathon.woofy.entity.Parent;

public interface ParentRepo extends JpaRepository<Parent, Long>{
	
	Parent findByUsername(String userName);

}
