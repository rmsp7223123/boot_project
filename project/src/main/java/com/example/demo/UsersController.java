package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import entity.Users;
import lombok.RequiredArgsConstructor;
import repository.UsersRepository;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersController {

	// private final UsersRepository userRepository;

	@PostMapping("/login")
	public String doLogin() {// 로그인
		return "home";
	}

	@GetMapping("/logout")
	public String logout() { // 로그아웃
		return "index";
	}

	@GetMapping("/signup")
	public String signup() { // 회원가입 이동
		return "signup";
	}
	
	@PostMapping("/signup")
    public String doSignup(Users user) {  // 회원가입
        return "";
    }
}
