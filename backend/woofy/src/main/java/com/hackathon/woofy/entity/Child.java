package com.hackathon.woofy.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.hackathon.woofy.request.UserRequest;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "child")
@Getter
@Setter
@ToString(of = { "id", "username", "password" })
public class Child {

	@Id
	@GeneratedValue
	@Column(name = "child_id")
	private Long id;

	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	private Parent parent;
	
//	@OneToMany(mappedBy = "child")
//	private List<Suspicious> suspicious = new ArrayList<>();
	
//	@OneToMany(mappedBy = "child")
//	private List<Mission> missions = new ArrayList<>();
	
	// 생성 메서드
	public Child() {}
	
	
	
	public Child(UserRequest userRequest, Parent parent) {
		super();
		this.username = userRequest.getUsername();
		this.password = userRequest.getPassword();
		this.firstName = userRequest.getFirstName();
		this.lastName = userRequest.getLastName();
		this.phoneNumber = userRequest.getPhoneNumber();
		this.parent = parent;
	}



	public Child(String username, String password, String firstName, String lastName, String phoneNumber,
			Parent parent) {
		super();
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.parent = parent;
	}

}