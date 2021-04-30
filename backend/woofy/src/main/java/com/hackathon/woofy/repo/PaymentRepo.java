package com.hackathon.woofy.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hackathon.woofy.entity.Parent;
import com.hackathon.woofy.entity.Payment;

public interface PaymentRepo extends JpaRepository<Payment, Long> {

	@Query("select p from Payment p where p.parent = :parent_id ")
	List<Payment> findByParent(@Param("parent_id") Parent parent);
}
