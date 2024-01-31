package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Users;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

	@RequestMapping("/")
	public String index(HttpSession session) {
		Users currentUser = (Users) session.getAttribute("user");
		if (currentUser != null) {
			return "redirect:/users/home";
		}
		return "index";
	}
}
