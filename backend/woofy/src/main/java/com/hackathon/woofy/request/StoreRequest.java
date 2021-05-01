package com.hackathon.woofy.request;

import com.hackathon.woofy.entity.Child;
import com.hackathon.woofy.entity.Parent;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreRequest {

	private String location; // 의심 지역 알림 서비스를 위함
	private int price; // 매장 물품의 가격
	private String bankCode;
	private String accountNumber;
	private String paymentCode;
}
