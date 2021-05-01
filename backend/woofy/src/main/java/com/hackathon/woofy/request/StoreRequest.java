package com.hackathon.woofy.request;

import com.hackathon.woofy.entity.Child;
import com.hackathon.woofy.entity.Parent;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreRequest {

	private int price; // 매장 물품의 가격
	
	private String location; // 의심 지역 알림 서비스를 위함
	
	public Parent parent; // 거래하는 사람이 부모인지
	public Child child; // 자식인지 판별하기 위함
	
}
