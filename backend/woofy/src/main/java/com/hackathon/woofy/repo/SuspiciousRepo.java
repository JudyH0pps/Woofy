package com.hackathon.woofy.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hackathon.woofy.entity.Child;
import com.hackathon.woofy.entity.Parent;
import com.hackathon.woofy.entity.Suspicious;

public interface SuspiciousRepo extends JpaRepository<Suspicious, Long>{

	@Query("select s from Suspicious s where s.child.parent = :parent_id ")
	List<Suspicious> findByParent(@Param("parent_id") Parent parent);
	
//	@Query("select s from Suspicious s where s.parent = :parent_id and s.child = :child_id")
//	List<Suspicious> findByParentAndChild(@Param("parent_id") Parent parent, @Param("child_id") Child child);
	
	@Query("select s from Suspicious s where s.child = :child_id and s.child.parent = :parent_id")
	List<Suspicious> findByParentAndChild(@Param("parent_id") Parent parent, @Param("child_id") Child child);
}
