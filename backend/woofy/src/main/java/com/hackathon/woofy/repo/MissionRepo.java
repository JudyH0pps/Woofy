package com.hackathon.woofy.repo;

import org.springframework.data.repository.CrudRepository;

import com.hackathon.woofy.entity.Mission;

public interface MissionRepo extends CrudRepository<Mission, Long>{

}
