package com.hackathon.woofy.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.hackathon.woofy.request.UserRequest;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "parent")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = { "id", "username", "password" })
public class Parent {

	@Id
	@GeneratedValue
	@Column(name = "parent_id")
	private Long id;

	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private int account;
	
	@OneToMany(mappedBy = "parent")
	private List<Child> childs = new ArrayList<>();

	public Parent(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public Parent(UserRequest userRequest) {
		super();
		this.username = userRequest.getUsername();
		this.password = userRequest.getPassword();
		this.firstName = userRequest.getFirstName();
		this.lastName = userRequest.getLastName();
		this.email = userRequest.getEmail();
	}

}