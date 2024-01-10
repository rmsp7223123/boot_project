package com.example.demo;

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
import com.example.demo.repository.UsersRepository;
import com.example.demo.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersController {

	private final UserService userService;
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

	@RequestMapping("/home")
	public String home() {
		return "";
	}

	@GetMapping("/checkId")
	@ResponseBody
	public String checkId(@RequestParam("mId") String id) {
		System.out.println("adfdasf");
		boolean isDuplicate = userRepository.findByLoginId(id).isPresent();
		return isDuplicate ? "1" : "0";
	}
}
