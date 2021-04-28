package com.hackathon.woofy.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
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

import com.hackathon.woofy.request.UserRequest;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "child")
@Getter @Setter
@ToString(of = { "id", "username", "password" })
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Child {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "child_id")
	private Long id;

	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String birthDay;	// format 처리 과정은 개발 단계에서 TBD
	private String authNum; // ������ȣ
	private boolean isAuth; // ���� �ߴ��� Ȯ��
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	private Parent parent;
	
//	@OneToMany(mappedBy = "child")
//	private List<Suspicious> suspicious = new ArrayList<>();
	
//	@OneToMany(mappedBy = "child")
//	private List<Mission> missions = new ArrayList<>();
	
	public Child(Map<String, Object> childObject, Parent parent) {
		super();
		this.username = (String)childObject.get("username");
		this.password = (String)childObject.get("password");
		this.firstName = (String)childObject.get("firstName");
		this.lastName = (String)childObject.get("lastName");
		this.phoneNumber = (String)childObject.get("phoneNumber");
		this.birthDay = (String)childObject.get("birthDay");
		this.parent = parent;
	}

}