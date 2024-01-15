package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.handler.LoginSuccessHandler;
import com.example.demo.handler.LogoutSuccessHandler;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final com.example.demo.repository.UsersRepository userRepository;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		return http.authorizeRequests()
				.requestMatchers("/users/login", "/users/register", "/users/signup", "/", "css/**", "datatables/**",
						"demo/**", "img/**", "jquery/**", "js/demo/**", "js/**", "mixins/**", "navs/**", "scss/**",
						"scss/mixins/**", "scss/utilities/**", "scss/vendor/**", "utilities/**", "vendor/**")
				.permitAll().requestMatchers("/users/home").authenticated().anyRequest().authenticated().and()
				.formLogin().loginPage("/") // 로그인페이지
				.usernameParameter("loginId") // 로그인에 사용될 id
				.passwordParameter("password") // 로그인에 사용될 password
				.failureUrl("/") // 로그인 실패 시 redirect 될 URL
				.successHandler(new LoginSuccessHandler(userRepository)) // 로그인 성공 시 실행 될 Handler
				.and().logout().logoutUrl("/users/logout") // 로그아웃 URL
				.invalidateHttpSession(true).deleteCookies("JSESSIONID") // 서버 측에서 클라이언트의 "JSESSIONID"라는 이름의 쿠키를 삭제
				// JSESSIONID는 톰캣 같은 서블릿 컨테이너가 세션을 관리하기 위해 사용하는 쿠키 이름
				.logoutSuccessHandler(new LogoutSuccessHandler()).and().build();
	}

}
