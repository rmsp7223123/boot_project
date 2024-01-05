package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeRequests().requestMatchers(new AntPathRequestMatcher("/test/test**")).authenticated() // requestMatchers
																											// -> 특정
																											// 리소스에 대해서
																											// 권한 설정
				.anyRequest().permitAll() // permitAll -> requestMatchers 설정한 리소스의 접근을 인증절차 없이 허용의미
				// test/test경로는 모두 인증
				.and().formLogin().loginPage("/login").loginProcessingUrl("/login-process").defaultSuccessUrl("/home")
				.failureUrl("/login?error=true").and().logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login?logout=true")
				.invalidateHttpSession(true).deleteCookies("JSESSIONID");
		return http.build();
	}
}
