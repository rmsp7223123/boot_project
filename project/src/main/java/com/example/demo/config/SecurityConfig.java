package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.handler.LoginFailureHandler;
import com.example.demo.handler.LoginSuccessHandler;
import com.example.demo.handler.LogoutSuccessHandler;
import com.example.demo.service.UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

//	private final com.example.demo.repository.UsersRepository userRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.authorizeRequests()
				.requestMatchers("/login", "/users/register","/users/findPassword", "/users/signup", "/", "css/**",
						"datatables/**", "demo/**", "img/**", "jquery/**", "js/demo/**", "js/**", "mixins/**",
						"navs/**", "scss/**", "scss/mixins/**", "scss/utilities/**", "scss/vendor/**", "utilities/**",
						"vendor/**")
				.permitAll().anyRequest().authenticated().and().formLogin().loginPage("/").loginProcessingUrl("/login_process")
				.successHandler(new LoginSuccessHandler(userService)).failureHandler(new LoginFailureHandler()).and().httpBasic();

		return http.build();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService);
	}

}
