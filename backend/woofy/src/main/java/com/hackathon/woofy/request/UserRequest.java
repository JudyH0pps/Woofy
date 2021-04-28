package com.hackathon.woofy.request;

import com.hackathon.woofy.entity.Child;
import com.hackathon.woofy.entity.Parent;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserRequest {
	
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String birthDay;
	private String phoneNumber;
	private String email;
	
	private Parent parent;
	private Child child;
}
