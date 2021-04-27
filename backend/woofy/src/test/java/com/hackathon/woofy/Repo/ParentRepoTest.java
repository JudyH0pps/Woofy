package com.hackathon.woofy.Repo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.hackathon.woofy.entity.Parent;
import com.hackathon.woofy.repo.ParentRepo;

@SpringBootTest
@Transactional
@Rollback(false)
public class ParentRepoTest {

	@Autowired ParentRepo parentRepo;
	
}
