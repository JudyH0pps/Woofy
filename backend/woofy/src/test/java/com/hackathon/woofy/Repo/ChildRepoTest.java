package com.hackathon.woofy.Repo;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.hackathon.woofy.entity.Child;
import com.hackathon.woofy.entity.Parent;
import com.hackathon.woofy.repo.ChildRepo;
import com.hackathon.woofy.repo.ParentRepo;

@SpringBootTest
@Transactional
@Rollback(false)
public class ChildRepoTest {

	@Autowired ParentRepo parentRepo;
	@Autowired ChildRepo childRepo;
	
	@Test
	public void basicCRUD() {
		Parent p1 = new Parent("parent1", "pwd1");
		Parent p2 = new Parent("parent2", "pwd1");
		parentRepo.save(p1);
		parentRepo.save(p2);
		
		Child c1 = new Child("child1", "pwd1", p1);
		Child c2 = new Child("child2", "pwd2", p1);
		Child c3 = new Child("child3", "pwd3", p2);
		Child c4 = new Child("child4", "pwd4", p1);
		Child c5 = new Child("child5", "pwd5", p2);
		childRepo.save(c1);
		childRepo.save(c2);
		childRepo.save(c3);
		childRepo.save(c4);
		childRepo.save(c5);
		
		List<Child> childs = childRepo.findAll();
		for(Child c : childs) {
			System.out.println("findAll(): " + c);
		}
	}
}
