package com.example.demo.handler;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.security.core.Authentication;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LogoutSuccessHandler
		implements org.springframework.security.web.authentication.logout.LogoutSuccessHandler {

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		pw.println("<script>alert('로그아웃 되었습니다.'); location.href='/';</script>");
		pw.flush();
	}

}
