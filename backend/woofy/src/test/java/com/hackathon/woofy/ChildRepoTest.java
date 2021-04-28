package com.hackathon.woofy;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.hackathon.woofy.entity.Child;
import com.hackathon.woofy.entity.Mission;
import com.hackathon.woofy.entity.Parent;
import com.hackathon.woofy.repo.ChildRepo;
import com.hackathon.woofy.repo.MissionRepo;
import com.hackathon.woofy.repo.ParentRepo;
import com.hackathon.woofy.request.MissionRequest;
import com.hackathon.woofy.request.UserRequest;

@SpringBootTest
@Transactional
@Rollback(false)
public class ChildRepoTest {

	@Autowired
	ChildRepo childRepo;
	
	@Autowired
	ParentRepo parentRepo;
	
	@Autowired
	MissionRepo missionRepo;
	
	@Test
	public void testMember() {
		
		// add mission
		UserRequest req = new UserRequest();
		req.setUsername("jugiaro5");
		Parent parent = new Parent(req);
		parentRepo.save(parent);
		List<Parent> all = parentRepo.findAll();
		
		UserRequest req2 = new UserRequest();
		req2.setUsername("BBB");
		Child child = new Child(req2, parent);
		childRepo.save(child);
		
		UserRequest req3 = new UserRequest();
		req3.setUsername("CCC");
		Child child2 = new Child(req3, parent);
		childRepo.save(child2);
		
		List<Child> list = childRepo.findByParent(parent);
		for(Child c : list) {
			System.out.println("CC " +c.toString());
		}
		
		Mission m = new Mission();
		m.setTitle("이거 해봐!");
		m.setContent("이거!");
		m.setChild(child2);
		m.setParent(parent);
		
		missionRepo.save(m);
		
		// select ALLmission
		List<Mission> list2 = missionRepo.findByParent(parent);
		for(Mission m22 : list2) {
			System.out.println("list2: " +m22);
		}
		
		List<Mission> list3 = missionRepo.findByParentAndChild(parent, child);
		for(Mission m3 : list3) {
			System.out.println("list3: " + m3.toString());
		}
	}
}
