package com.example.demo.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.demo.entity.Users;
import com.example.demo.repository.UsersRepository;
import com.example.demo.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

//	private final UsersRepository userRepository;

	private final UserService userService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(3600); // 세션유지 3600초

		Users loginUser = userService.findUserByLoginId(authentication.getName());
		List<GrantedAuthority> authorities;
		if (loginUser.getIsAdmin()) {
			authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
		} else {
			authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
		}
		Authentication auth = new UsernamePasswordAuthenticationToken(loginUser, null, authorities);
		session.setAttribute("user", loginUser);
		SecurityContextHolder.getContext().setAuthentication(auth);

		response.setContentType("text/html"); // 응답의 컨텐츠 타입을 HTML로 설정
		PrintWriter pw = response.getWriter(); // 응답에 쓸 PrintWriter 객체
		if (loginUser.getIsAdmin() == true) {
			session.setAttribute("toastMessage", "관리자로 로그인되었습니다.");
			pw.println("<script>alert('관리자로 로그인되었습니다.'); window.location.href='/users/home';</script>");
		} else {
			session.setAttribute("toastMessage", "관리자로 로그인되었습니다.");
			pw.println("<script>alert('" + loginUser.getNickname()
					+ "님 반갑습니다.'); window.location.href='/users/home';</script>");
		}
		pw.flush(); // PrintWriter의 버퍼를 비우고, 버퍼에 남아있는 모든 문자를 출력
	}

}
