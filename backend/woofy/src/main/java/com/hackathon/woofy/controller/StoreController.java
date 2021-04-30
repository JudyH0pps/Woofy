package com.hackathon.woofy.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hackathon.woofy.entity.Child;
import com.hackathon.woofy.entity.Parent;
import com.hackathon.woofy.entity.Payment;
import com.hackathon.woofy.entity.PaymentStatus;
import com.hackathon.woofy.request.StoreRequest;
import com.hackathon.woofy.response.BasicResponse;
import com.hackathon.woofy.service.ChildService;
import com.hackathon.woofy.service.ParentService;
import com.hackathon.woofy.service.PaymentService;
import com.hackathon.woofy.util.WooriFunc;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/store")
public class StoreController {

	private final ParentService parentService;
	private final ChildService childService;
	private final PaymentService paymentService;

	private WooriFunc wf;

	@PostMapping(value = "", produces = "application/json; charset=utf8")
	public Object storePayReq(@RequestBody StoreRequest storeRequset) {
		final BasicResponse basicResponse = new BasicResponse();
		
		System.out.println("storeRequset: "+storeRequset.toString());
		
		Parent parent = null;
		Child child = null;
		Payment payment  = new Payment(); // 거래내역 저장;
		boolean isChild = false; // 부모인지 자식인지 판별
		boolean isLimit = false; // 한도초과인지 확인
		
		String WDR_ACNO = ""; // 출금계좌번호

		try {
			Map<String, Object> map = new HashMap<>();

			if (storeRequset.getParent() != null) { // 부모가 거래할 시
				parent = parentService.findParent(storeRequset.getParent().getUsername());
				
				System.out.println("Parent: " + parent.toString());
				WDR_ACNO = parent.getAccount();
				
			} else { // 자식이 거래할 시
				isChild = true;
				
				child = childService.findByUsername(storeRequset.getChild().getUsername());
				WDR_ACNO = child.getParent().getAccount();

				if (child.getSpendLimit() < storeRequset.getPrice()) { // 한도 초과일 때
					isLimit = true;
					
					payment.setDate(LocalDateTime.now());
					payment.setLocation(storeRequset.getLocation());
					payment.setPrice(storeRequset.getPrice());
					payment.setChildNum(child.getId());
					payment.setParent(child.getParent());
					payment.setPaymentStatus(PaymentStatus.FAIL);
					
					paymentService.savePayment(payment);
					
					// 추가적으로 한도 넘으면 부모에게 알림 할지말지 정하자
					
					basicResponse.dataBody = payment;
					basicResponse.status = "limit";
				} 
			}
			
			
			if(!isLimit) {
//				String api = wf.executeWooriAcctToWooriAcct(WDR_ACNO, storeRequset.getPrice() + "", "020", "1002987654321", "입금"); // Only Request
//				System.out.println(api);
				
				payment.setDate(LocalDateTime.now());
				payment.setLocation(storeRequset.getLocation());
				payment.setPrice(storeRequset.getPrice());
				if(isChild) {
					payment.setChildNum(child.getId());
					payment.setParent(child.getParent());
				} else {
					payment.setParent(parent);
				}
				payment.setPaymentStatus(PaymentStatus.SUCCESS);
				
				paymentService.savePayment(payment);
				
				basicResponse.dataBody = payment;
				basicResponse.status = "success";
			}


		} catch (Exception e) {
			basicResponse.status = "error";
			e.printStackTrace();
		} finally {
			return new ResponseEntity<>(basicResponse, HttpStatus.OK);
		}
	}
}
