package com.hackathon.woofy.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hackathon.woofy.entity.Parent;
import com.hackathon.woofy.entity.Payment;
import com.hackathon.woofy.repo.PaymentRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {

	private final PaymentRepo paymentRepo;
	
	@Transactional
	public void savePayment(Payment payment) {
		paymentRepo.save(payment);
	}
	
	@Transactional
	public List<Payment> findByParent(Parent parent){
		return paymentRepo.findByParent(parent);
	}
}
