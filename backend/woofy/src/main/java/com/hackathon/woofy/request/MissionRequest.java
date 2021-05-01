package com.hackathon.woofy.request;

import java.util.List;

import com.hackathon.woofy.entity.Mission;
import com.hackathon.woofy.entity.MissionDetail;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
public class MissionRequest {
	
	private Mission mission;
	private MissionDetail missiondetail;
	private List<MissionDetail> missiondetailList;
	private String childUsername;
	
	@Override
	public String toString() {
		return "MissionRequest [mission=" + mission + "\n, missiondetail=" + missiondetailList + "\n, childUsername="
				+ childUsername + "]";
	}
	
}
