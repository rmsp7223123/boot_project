package com.example.demo;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.dto.UserJoinRequest;
import com.example.demo.entity.Users;
import com.example.demo.repository.UsersRepository;
import com.example.demo.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersController {

	private final UserService userService;
	private final UsersRepository userRepository;

	@Autowired
	private final BCryptPasswordEncoder passwordEncoder;

	@RequestMapping("/home")
	public String home() { // 로그인 후 홈화면 이동
		return "home";
	}

	@RequestMapping("/login")
	public String doLogin(@RequestParam("mId") String loginId, @RequestParam("mPassword") String password) {// 로그인
		boolean isDuplicate = userRepository.findByLoginIdAndPassword(loginId, password).isPresent();
		return isDuplicate ? "1" : "0";
	}

	@GetMapping("/logout")
	public String logout() { // 로그아웃
		return "index";
	}

	@RequestMapping("/register")
	public String register() { // 회원가입 이동
		return "login/register";
	}

	@PostMapping("/signup")
	public String doSignup(@Validated @ModelAttribute UserJoinRequest userJoinRequest, BindingResult bindingResult) { // 회원가입
		bindingResult = userService.joinValid(userJoinRequest, bindingResult);

		if (bindingResult.hasErrors()) {
			for (FieldError error : bindingResult.getFieldErrors()) {
				System.out.println("Field: " + error.getField());
				System.out.println("Message: " + error.getDefaultMessage());
			}
			return "error";
		}

		userService.join(userJoinRequest);

		return "redirect:/";
	}

	@GetMapping("/checkId")
	@ResponseBody
	public String checkId(@RequestParam("mId") String id) { // 아이디 중복검사
		boolean isDuplicate = userRepository.findByLoginId(id).isPresent();
		return isDuplicate ? "1" : "0";
	}

	@GetMapping("/checkNickname")
	@ResponseBody
	public String checkNickname(@RequestParam("mNickname") String nickname) { // 닉네임 중복검사
		boolean isDuplicate = userRepository.findByNickname(nickname).isPresent();
		return isDuplicate ? "1" : "0";
	}

	@RequestMapping("/findPassword")
	public String findPassword() { // 비밀번호 찾기 화면 이동
		return "login/findPassword";
	}

	@RequestMapping("/passwordRecovery")
	public ResponseEntity<String> passwordRecovery(@RequestParam("mId") String loginId,
			@RequestParam("mName") String name) { // 비밀번호 찾기
		Optional<Users> userOptional = userRepository.findByLoginIdAndName(loginId, name);
		if (userOptional.isPresent()) {
			Users user = userOptional.get();
			if (user.getName().equals(name) && user.getLoginId().equals(loginId)) {
				String tempPassword = createTempPassword();
				user.setPassword(passwordEncoder.encode(tempPassword));
				userRepository.save(user);
				return ResponseEntity.ok(tempPassword);
			}
		}
		return ResponseEntity.ok("fail");
	}

	public String createTempPassword() { // 비밀번호 찾기용 임시 비밀번호 생성 8글자의 영어(소문자) 숫자 조합
		StringBuilder sb = new StringBuilder();
		Random rand = new Random();
		for (int i = 0; i < 8; i++) {
			int index = rand.nextInt(2);
			switch (index) {
			case 0:
				sb.append((char) (rand.nextInt(26) + 97)); // a-z
				break;
			case 1:
				sb.append(rand.nextInt(10)); // 0-9
				break;
			}
		}
		return sb.toString();
	}

}
