package com.hackathon.woofy.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hackathon.woofy.entity.Child;
import com.hackathon.woofy.entity.Mission;
import com.hackathon.woofy.entity.Parent;
import com.hackathon.woofy.entity.Payment;
import com.hackathon.woofy.entity.PaymentStatus;
import com.hackathon.woofy.request.PaymentRequest;
import com.hackathon.woofy.response.BasicResponse;
import com.hackathon.woofy.service.ChildService;
import com.hackathon.woofy.service.ParentService;
import com.hackathon.woofy.service.PaymentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment")
public class PaymentController {

	private final PaymentService paymentService;
	private final ParentService parentService;
	private final ChildService childService;

	/**
	 * 부모/자녀 거래내역 저장
	 * 
	 * @param payment
	 * @return
	 */
	@PostMapping(value = "", produces = "application/json; charset=utf8")
	public Object saveParentPayment(@RequestBody PaymentRequest paymentRequest) {
		final BasicResponse basicResponse = new BasicResponse();

		try {
			Map<String, Object> map = new HashMap<>();

			Payment payment = new Payment();

			if (paymentRequest.getChild() == null) { // 부모가 거래한 경우
				Parent parent = parentService.findParent(paymentRequest.getParent().getUsername());

				payment.setDate(LocalDateTime.now()); // 현재시간 저장
				payment.setLocation(paymentRequest.getLocation());
				payment.setPrice(paymentRequest.getPrice());
				payment.setPaymentStatus(PaymentStatus.SUCCESS); // 부모는 무조건 success (결제 성공)
				payment.setParent(parent);

				basicResponse.status = "200";

			} else { // 자녀가 거래한 경우
				Child child = childService.findByUsername(paymentRequest.getChild().getUsername());
				Parent parent = parentService.findParent(child.getParent().getUsername());

				if (child.getSpendLimit() < paymentRequest.getPrice()) { // 한도 초과인 경우 결제 실패

					payment.setDate(LocalDateTime.now()); // 현재시간 저장
					payment.setLocation(paymentRequest.getLocation());
					payment.setPrice(paymentRequest.getPrice());
					payment.setPaymentStatus(PaymentStatus.FAIL); // 부모는 무조건 success (결제 성공)
					payment.setParent(parent);

					payment.setChildNum(child.getId());

					basicResponse.status = "200 impossible";
				} else {

					payment.setDate(LocalDateTime.now()); // 현재시간 저장
					payment.setLocation(paymentRequest.getLocation());
					payment.setPrice(paymentRequest.getPrice());
					payment.setPaymentStatus(PaymentStatus.SUCCESS); // 부모는 무조건 success (결제 성공)
					payment.setParent(parent);

					payment.setChildNum(child.getId());

					basicResponse.status = "200";
				}
			}
			paymentService.savePayment(payment);

		} catch (Exception e) {
			basicResponse.status = "400";
			e.printStackTrace();
		} finally {
			return new ResponseEntity<>(basicResponse, HttpStatus.OK);
		}
	}

	/**
	 * 부모는 자신의 모든 거래내역(자식 포함)을 조회
	 * @param p_username
	 * @return
	 */
	/*
	@GetMapping(value = "/{p_username}/childs", produces = "application/json; charset=utf8")
	public Object findByParent(@PathVariable(name = "p_username") String p_username) {

		final BasicResponse basicResponse = new BasicResponse();

		try {
			Map<String, Object> map = new HashMap<>();

			Parent p = parentService.findParent(p_username);

			List<Mission> result = missionService.findByParent(p);

			map.put("mission", result);

			if (result.size() != 0) {
				basicResponse.dataBody = map;
				basicResponse.status = "200";

			} else {
				basicResponse.dataBody = map;
				basicResponse.status = "200 none";
			}

		} catch (Exception e) {
			basicResponse.status = "400";
			e.printStackTrace();
		} finally {
			return new ResponseEntity<>(basicResponse, HttpStatus.OK);
		}
	}
	*/
}
