package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 아무런 매개변수가 없는 생성자를 생성하되 다른 패키지에 소속된 클래스는 접근을 불허
@Getter
@Setter
@Entity
public class Users {

	@Id // pk
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키의 값을 자동으로 생성, 기본 키 값을 자동 증가
	private Long Id; // 기본 키(primary key)는 유일한 값을 가지고 있어야 하며, 큰 범위의 값을 지원해야함
						// long 타입은 64비트의 부호 있는 정수로 매우 큰 범위의 값을 표현할 수 있으므로, 고유한 식별자로서 적합

	private String loginId;

	private String password;

	private String name;

	private String nickname;

	private String gender;

	private int age;

	@Column(columnDefinition = "NUMBER(1,0)")
	private Boolean isAdmin;
	// boolean
	// 기본형 ( primitive type ) , 메모리에 직접 값을 저장
	// null을 담을 수 없으며 true,false만
	
	// Boolean
	// 참조형 ( reference type ) , 참조값 ( 주소 ) 을 가진다.
	// null을 담을 수 있음

	@Builder
	public Users(Long Id, String loginId, String password, String name, String nickname, String gender, int age,
			boolean isAdmin) {
		this.Id = Id;
		this.loginId = loginId;
		this.password = password;
		this.name = name;
		this.nickname = nickname;
		this.gender = gender;
		this.age = age;
		this.isAdmin = isAdmin;
	}

	public void update(Users updateUser) {
		
		this.password = updateUser.password;
		this.name = updateUser.name;
		this.nickname = updateUser.nickname;
		this.gender = updateUser.gender;
		this.age = updateUser.age;
		if(updateUser.isAdmin != null) {
	        this.isAdmin = updateUser.isAdmin;
	    }
	}

}
