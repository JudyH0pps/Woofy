package com.hackathon.woofy.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hackathon.woofy.entity.Mission;
import com.hackathon.woofy.entity.MissionDetail;

public interface MissionDetailRepo extends JpaRepository<MissionDetail, Long>{

	@Query("select md from MissionDetail md where md.mission = :mission_id")
	List<MissionDetail> findByMission(@Param("mission_id") Mission mission);
}
