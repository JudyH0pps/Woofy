package com.hackathon.woofy.entity;

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

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter
@Table(name = "MissionDetail")
public class MissionDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "missiondetail_id")
	private Long id;
	
	private String title;
	private String content;
	private int prize;
	private String imageUrl;
	
	@Enumerated(EnumType.STRING)
	private MissionStatus missionStatus = MissionStatus.ONGOING;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mission_id")
	private Mission mission;

	@Override
	public String toString() {
		return "MissionDetail [id=" + id + ", title=" + title + ", content=" + content + ", prize=" + prize
				+ ", imageUrl=" + imageUrl + ", missionStatus=" + missionStatus + ", mission=" + mission + "]";
	}
	
	
}
