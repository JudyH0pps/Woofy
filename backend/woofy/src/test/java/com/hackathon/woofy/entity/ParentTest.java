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
public class ParentTest {

	@PersistenceContext
	EntityManager em;

	@Test
	public void testEntity() {
		Parent parent = new Parent("parent1", "pwd1");

		em.persist(parent); // persist: ���Ӽ� ���ý�Ʈ�� ��� ���´�.

		// �ʱ�ȭ
		em.flush(); // ������ DB�� insert ������ ������.
		em.clear(); // ���Ӽ� ���ؽ�Ʈ�� ĳ�ø� ������.
		
		List<Parent> parents = em.createQuery("select p from Parent p",Parent.class).getResultList();
		
		for(Parent p : parents) {
			System.out.println("Parent: " + p);
		}
	}
	
}
