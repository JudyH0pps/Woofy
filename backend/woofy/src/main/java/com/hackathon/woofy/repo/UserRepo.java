package com.hackathon.woofy.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hackathon.woofy.entity.User;

public interface UserRepo extends JpaRepository<User, Long>{
	User findByUsername(String userName);
	User findByPhoneNumber(String userName);
}
