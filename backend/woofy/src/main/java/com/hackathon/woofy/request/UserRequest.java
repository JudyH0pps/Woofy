package com.hackathon.woofy.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserRequest {
	
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private int birth;
	private String phoneNumber;
	
	@Override
	public String toString() {
		return this.getPassword() + " " + this.getEmail() + " ";
	}

}
