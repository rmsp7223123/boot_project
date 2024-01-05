package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import handler.LoginSuccessHandler;
import handler.LogoutSuccessHandler;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final repository.UsersRepository userRepository;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		return http.formLogin().loginPage("/users/login") // 로그인 페이지
				.usernameParameter("loginId") // 로그인에 사용될 id
				.passwordParameter("password") // 로그인에 사용될 password
				.failureUrl("/users/login?fail") // 로그인 실패 시 redirect 될 URL => 실패 메세지 출력
				.successHandler(new LoginSuccessHandler(userRepository)) // 로그인 성공 시 실행 될 Handler
				.and().logout().logoutUrl("/users/logout") // 로그아웃 URL
				.invalidateHttpSession(true).deleteCookies("JSESSIONID") // 서버 측에서 클라이언트의 "JSESSIONID"라는 이름의 쿠키를 삭제
				// JSESSIONID는 톰캣 같은 서블릿 컨테이너가 세션을 관리하기 위해 사용하는 쿠키 이름
				.logoutSuccessHandler(new LogoutSuccessHandler()).and().build();
	}
}
