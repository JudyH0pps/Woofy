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

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class Child {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "child_id")
	private Long id;
	private String firstName;
	private String lastName;
	private String birthDay;	// format 泥섎━ 怨쇱젙�� 媛쒕컻 �떒怨꾩뿉�꽌 TBD
	private String authNum; // 占쏙옙占쏙옙占쏙옙호
	private int spendLimit; // 자녀 한도
	private boolean isAuth = false;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	private Parent parent;
		
//	@OneToMany(mappedBy = "child",cascade = CascadeType.ALL)
//	private List<Suspicious> suspicious = new ArrayList<>();
	
//	@OneToMany(mappedBy = "child", cascade = CascadeType.ALL)
//	private List<Mission> missions = new ArrayList<>();
	
	// 생성 메서드
	public Child() {}
	
	public Child(UserRequest userRequest, Parent parent) {
		super();
		this.firstName = userRequest.getFirstName();
		this.lastName = userRequest.getLastName();
		this.birthDay = userRequest.getBirthDay();
		this.spendLimit = userRequest.getSpendLimit();
		this.parent = parent;
	}
	
	public Child(Map<String, Object> childObject, Parent parent) {
		super();
		this.firstName = (String)childObject.get("firstName");
		this.lastName = (String)childObject.get("lastName");
		this.birthDay = (String)childObject.get("birthDay");
		this.parent = parent;
	}
	
	public boolean increaseSpendLimit(int money) {
		this.spendLimit += money;
		return true;
	}
	
	public boolean decreaseSpendLimit(int money) {
		if (this.spendLimit - money < 0) {
			return false;
		}
		
		this.spendLimit -= money;
		return true;
	}
	
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public String getUsername() {
        return this.user.getUsername();
    }

	@Override
	public String toString() {
		return "Child [id=" + id + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", birthDay=" + birthDay + ", authNum="
				+ authNum + ", isAuth=" + isAuth + ", parent=" + parent + "]";
	}
}