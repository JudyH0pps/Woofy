package com.hackathon.woofy.entity;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Rollback(false)
public class ChildTest {
	
	@PersistenceContext
	EntityManager em;

	@Test
	public void testEntity() {
		Parent parent = new Parent("parent1", "pwd1");
		em.persist(parent); // persist: 영속성 컨택스트에 담아 놓는다.
		
		Child child = new Child("child1","pwd1",parent);
		em.persist(child);
		
		Child child2 = new Child("child2","pwd2",parent);
		em.persist(child2);

		// 초기화
		em.flush(); // 강제로 DB에 insert 쿼리를 날린다.
		em.clear(); // 영속성 컨텍스트의 캐시를 날린다.
		
		List<Child> childs = em.createQuery("select c from Child c",Child.class).getResultList();
		
		for(Child c : childs) {
			System.out.println("Child: " + c);
		}
	}
}
