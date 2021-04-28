package com.hackathon.woofy.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

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
@ToString(of = { "id", "username", "password" })
public class Parent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "parent_id")
	private Long id;

	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private String birthDay;	// format 처리 과정은 개발 단계에서 TBD
	private String account;
	private String authNum; // ������ȣ
	private boolean isAuth; // ���� �ߴ��� Ȯ��
	
//	@OneToMany(mappedBy = "parent")
//	private List<Child> childs = new ArrayList<>(); // �б⸸ ����
	
	public Parent() {};
	
	public Parent(UserRequest userRequest) {
		super();
		this.username = userRequest.getUsername();
		this.password = userRequest.getPassword();
		this.firstName = userRequest.getFirstName();
		this.lastName = userRequest.getLastName();
		this.email = userRequest.getEmail();
		this.phoneNumber = userRequest.getPhoneNumber();
		this.birthDay = userRequest.getBirthDay();
	}

	public Parent(Map<String, Object> parentObject) {
		super();
		this.username = (String)parentObject.get("username");
		this.password = (String)parentObject.get("password");
		this.firstName = (String)parentObject.get("firstName");
		this.lastName = (String)parentObject.get("lastName");
		this.email = (String)parentObject.get("email");
		this.phoneNumber = (String)parentObject.get("phoneNumber");
		this.birthDay = (String)parentObject.get("birthDay");
		this.account = (String)parentObject.get("accountNumber");
	}

//	public void addChild(Child child) {
//		child.setParent(this);
//		childs.add(child);
//	}

//	@OneToMany(mappedBy = "parent")
//	private List<Child> childs = new ArrayList<>(); // �б⸸ ����
	
}