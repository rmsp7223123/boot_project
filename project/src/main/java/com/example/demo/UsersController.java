package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Users;
import com.example.demo.repository.UsersRepository;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersController {

	private final UsersRepository userRepository;

	@RequestMapping("/login")
	public String doLogin() {// 로그인
		return "index";
	}

	@GetMapping("/logout")
	public String logout() { // 로그아웃
		return "index";
	}

	@RequestMapping("/register")
	public String register() { // 회원가입 이동
		return "register";
	}

	@PostMapping("/signup")
	public String doSignup(Users user) { // 회원가입
		userRepository.save(user);
        return "index";
	}
	
	@RequestMapping("/home")
	public String home() {
		return "";
	}
}
