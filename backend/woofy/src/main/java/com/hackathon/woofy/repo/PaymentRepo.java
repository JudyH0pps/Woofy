package com.hackathon.woofy.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hackathon.woofy.entity.Payment;

public interface PaymentRepo extends JpaRepository<Payment, Long> {

}
