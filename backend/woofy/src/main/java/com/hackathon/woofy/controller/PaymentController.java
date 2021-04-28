package com.hackathon.woofy.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment")
public class PaymentController {
	@PostMapping("")
	public String makePayment(@RequestBody Map<String, Object> jsonRequest) {
		Map<String, Object> paymentRequestBody = (Map<String, Object>) jsonRequest.get("dataBody");
		System.out.println(paymentRequestBody);
		
		return "DEBUG";
	}
}
