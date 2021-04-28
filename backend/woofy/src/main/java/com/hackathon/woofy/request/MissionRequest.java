package com.hackathon.woofy.request;

import com.hackathon.woofy.entity.Child;
import com.hackathon.woofy.entity.MissionStatus;
import com.hackathon.woofy.entity.Parent;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MissionRequest {
	
	private String title;
	private String content;
	private int price;
	private MissionStatus missionStatus;
	
	private Parent parent;
	private Child child;
}
