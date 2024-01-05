package entity;

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
@AllArgsConstructor
@Getter
@Entity
@Builder
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

	private boolean isAdmin;


}
