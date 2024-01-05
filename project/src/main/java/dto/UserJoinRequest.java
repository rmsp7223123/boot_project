package dto;

import entity.Users;
import lombok.Data;

@Data
public class UserJoinRequest {

	private String loginId;
	private String nickname;
	private String password;
	private String passwordCheck;
	private String gender;
	private int age;

	public Users toEntity(String encodedPassword) {
		return Users.builder().loginId(loginId).password(encodedPassword).nickname(nickname).gender(gender).age(age)
				.isAdmin(false).build();
	}

}
