package com.hackathon.woofy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
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
@ToString(of = { "id", "title", "parent", "child" })
public class Mission {
	
	@Id
	@GeneratedValue
	@Column(name = "mission_id")
	private Long id;
	
	private String title;
	private String content;
	private int price;
	
	@Enumerated(EnumType.STRING)
	private MissionStatus missionStatus;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "child_id")
	private Child child;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	private Parent parent;
	
	public Mission() {}
	
	public Mission(MissionRequest missionRequest) {
		super();
		this.title = missionRequest.getTitle();
		this.content = missionRequest.getContent();
		this.price = missionRequest.getPrice();
		this.missionStatus = missionRequest.getMissionStatus();
		this.parent = missionRequest.getParent();
		this.child = missionRequest.getChild();
	}	
	
}
