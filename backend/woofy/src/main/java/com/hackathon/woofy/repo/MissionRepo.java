package com.hackathon.woofy.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hackathon.woofy.entity.Child;
import com.hackathon.woofy.entity.Mission;
import com.hackathon.woofy.entity.Parent;

public interface MissionRepo extends JpaRepository<Mission, Long>{
	@Query("select m from Mission m where m.child.parent = :parent_id ")
	List<Mission> findByParent(@Param("parent_id") Parent parent);
	
	@Query("select m from Mission m where m.child = :child_id")
	List<Mission> findByChild(@Param("child_id") Child child);

	@Query("select m from Mission m where m.child = :child_id and m.child.parent = :parent_id")
	List<Mission> findByParentAndChild(@Param("parent_id") Parent parent, @Param("child_id") Child child);
}
