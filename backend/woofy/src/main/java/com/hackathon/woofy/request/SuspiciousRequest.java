package com.hackathon.woofy.request;

import java.time.LocalDateTime;

import com.hackathon.woofy.entity.Child;
import com.hackathon.woofy.entity.Parent;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SuspiciousRequest {

	private String location;
	private String startTime;
	private String endTime;
	
	private Child child;
	private Parent parent;
}
