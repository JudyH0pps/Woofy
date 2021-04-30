package com.hackathon.woofy.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hackathon.woofy.request.UserRequest;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity @Table(name = "parent") 
@Getter @Setter
public class Parent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "parent_id")
	private Long id;

	private String firstName;
	private String lastName;
	private String email;
	private String birthDay;
	private String account;
	private String authNum;
	private boolean isAuth = false;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
//	@OneToMany(mappedBy = "parent")
//	private List<Child> childs = new ArrayList<>();
	
	public Parent() {};
	
	public Parent(UserRequest userRequest) {
		super();
		this.firstName = userRequest.getFirstName();
		this.lastName = userRequest.getLastName();
		this.email = userRequest.getEmail();
		this.birthDay = userRequest.getBirthDay();
	}

	public Parent(Map<String, Object> parentObject) {
		super();
		this.firstName = (String)parentObject.get("firstName");
		this.lastName = (String)parentObject.get("lastName");
		this.email = (String)parentObject.get("email");
		this.birthDay = (String)parentObject.get("birthDay");
		this.account = (String)parentObject.get("accountNumber");
	}
	
	@Override
	public String toString() {
		return "Parent [id=" + id + ", password=" + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", email=" + email + ", phoneNumber=" + ", birthDay="
				+ birthDay + ", account=" + account + ", authNum=" + authNum + ", isAuth=" + isAuth + "]";
	}
	
    public String getUsername() {
        return this.user.getUsername();
    }

//	public void addChild(Child child) {
//		child.setParent(this);
//		childs.add(child);
//	}

//	@OneToMany(mappedBy = "parent")
//	private List<Child> childs = new ArrayList<>(); // �б⸸ ����
	
	
	
}