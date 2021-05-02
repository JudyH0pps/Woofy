package com.hackathon.woofy.entity;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.hackathon.woofy.request.MissionRequest;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter
@Table(name = "mission")
public class Mission {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "mission_id")
	private Long id;
	
	private String title; // 미션 제목
	private int totalPrice; // 총 보상금액(세부 미션 합산)
	private int isDaily; // 매일 미션인 경우 1 아니면 2
	private int isRequest; // 부모가 등록한 경우 1 / 자식이 요청한 경우 2
	private int isComplete; // 세부 미션이 모두 완료되면 1 아니면 2
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "child_id")
	private Child child;
	
	public Mission() {}

	@Override
	public String toString() {
		return "Mission [id=" + id + ", title=" + title + ", totalPrice=" + totalPrice + ", isDaily=" + isDaily
				+ ", isRequest=" + isRequest + ", isComplete=" + isComplete + ", child=" + child + "]";
	}
	
	/*
	public Mission(MissionRequest missionRequest) {
		super();
		this.title = missionRequest.getTitle();
		this.content = missionRequest.getContent();
		this.prize = missionRequest.getPrize();
		this.missionStatus = missionRequest.getMissionStatus();
//		this.parent = missionRequest.getParent();
//		this.child = missionRequest();
	}	


	public Mission(Map<String, Object> missionRequestObject, Parent parent, Child child) {
		super();
		this.title = (String)missionRequestObject.get("title");
		this.content = (String)missionRequestObject.get("content");
		this.prize = (int)missionRequestObject.get("prize");
		this.child = child;
//		this.parent = parent;
	}
	*/
	
	
	
}
