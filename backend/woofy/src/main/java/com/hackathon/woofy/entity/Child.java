package com.hackathon.woofy.entity;

import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.hackathon.woofy.request.UserRequest;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "child")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	private Parent parent;
	
	// 생성 메서드
	public Child(String username, String password, Parent parent) {
		this.username = username;
		this.password = password;
		this.parent = parent;
		parent.getChilds().add(this);
	}
	
	public Child(UserRequest userRequest) {
		super();
		this.username = userRequest.getUsername();
		this.password = userRequest.getPassword();
		this.firstName = userRequest.getFirstName();
		this.lastName = userRequest.getLastName();
	}

}