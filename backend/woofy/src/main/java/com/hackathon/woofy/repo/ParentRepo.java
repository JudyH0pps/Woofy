package com.hackathon.woofy.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hackathon.woofy.entity.Parent;

public interface ParentRepo extends JpaRepository<Parent, Long>{
	
	Parent findOne(long id);
}
