package com.hackathon.woofy.request;

import com.hackathon.woofy.entity.Child;
import com.hackathon.woofy.entity.Parent;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransinfoRequest {

	private String location;
	private int price;
	
	private Parent parent;
	private Child child;
}
