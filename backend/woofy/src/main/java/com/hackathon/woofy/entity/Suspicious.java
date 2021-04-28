package com.hackathon.woofy.entity;

import lombok.Setter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hackathon.woofy.request.SuspiciousRequest;

@Entity
@Getter
@Setter
@Table(name = "suspicious")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Suspicious {

	@Id
	@GeneratedValue
	@Column(name = "suspicious_id")
	private Long id;

	private String location;

	private String startTime;
	private String endTime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "child_id")
	private Child child;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	private Parent parent;

	public Suspicious(SuspiciousRequest suspiciousRequest) {
		super();
		this.location = suspiciousRequest.getLocation();
		this.startTime = suspiciousRequest.getStartTime();
		this.endTime = suspiciousRequest.getEndTime();
		this.child = suspiciousRequest.getChild();
		this.parent = suspiciousRequest.getParent();
	}

}
