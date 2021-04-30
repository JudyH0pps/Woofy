package com.hackathon.woofy.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.hackathon.woofy.entity.Parent;

public interface ParentRepo extends JpaRepository<Parent, Long>{

	@Query("select p from Parent p where p.user.username = :username")
	Parent findByUsername(String username);
}
