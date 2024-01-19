package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.handler.LoginSuccessHandler;
import com.example.demo.handler.LogoutSuccessHandler;
import com.example.demo.service.UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final com.example.demo.repository.UsersRepository userRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.authenticationProvider(authenticationProvider())
				.csrf().disable().cors().and().authorizeRequests()
				.requestMatchers("/users/login", "/login", "/users/register", "/users/signup", "/", "css/**",
						"datatables/**", "demo/**", "img/**", "jquery/**", "js/demo/**", "js/**", "mixins/**",
						"navs/**", "scss/**", "scss/mixins/**", "scss/utilities/**", "scss/vendor/**", "utilities/**",
						"vendor/**")
				.permitAll().anyRequest().authenticated().and().formLogin().loginPage("/").usernameParameter("loginId")
				.passwordParameter("password").successHandler(new LoginSuccessHandler(userRepository)).and().logout()
				.logoutUrl("/users/logout").invalidateHttpSession(true).deleteCookies("JSESSIONID")
				.logoutSuccessHandler(new LogoutSuccessHandler());
		return http.build();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userService);
		authProvider.setPasswordEncoder(passwordEncoder);
		return authProvider;
	}

}
