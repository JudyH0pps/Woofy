package com.hackathon.woofy.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hackathon.woofy.entity.Parent;
import com.hackathon.woofy.entity.Transinfo;

public interface TransinfoRepo extends JpaRepository<Transinfo, Long> {

	@Query("select t from Transinfo t where t.parent = :parent_id ")
	List<Transinfo> findByParent(@Param("parent_id") Parent parent);
	
	@Query("select t from Transinfo t where t.childNum = :child_id ")
	List<Transinfo> findByChild(@Param("child_id") Long child_id);
}
