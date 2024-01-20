package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import com.example.demo.dto.UserDTO;
import com.example.demo.dto.UserJoinRequest;
import com.example.demo.entity.Users;
import com.example.demo.repository.UsersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

	private final UsersRepository userRepository;
	private final BCryptPasswordEncoder encoder;

	public BindingResult joinValid(UserJoinRequest req, BindingResult bindingResult) {
		if (req.getLoginId().isEmpty()) {
			bindingResult.addError(new FieldError("req", "loginId", "아이디가 비어있습니다."));
		} else if (req.getLoginId().length() > 10) {
			bindingResult.addError(new FieldError("req", "loginId", "아이디가 10자가 넘습니다."));
		} else if (userRepository.existsByLoginId(req.getLoginId())) {
			bindingResult.addError(new FieldError("req", "loginId", "아이디가 중복됩니다."));
		}

		if (req.getPassword().isEmpty()) {
			bindingResult.addError(new FieldError("req", "password", "비밀번호가 비어있습니다."));
		}

		if (!req.getPassword().equals(req.getPasswordCheck())) {
			bindingResult.addError(new FieldError("req", "passwordCheck", "비밀번호가 일치하지 않습니다."));
		}

		if (req.getNickname().isEmpty()) {
			bindingResult.addError(new FieldError("req", "nickname", "닉네임이 비어있습니다."));
		} else if (req.getNickname().length() > 10) {
			bindingResult.addError(new FieldError("req", "nickname", "닉네임이 10자가 넘습니다."));
		} else if (userRepository.existsByNickname(req.getNickname())) {
			bindingResult.addError(new FieldError("req", "nickname", "닉네임이 중복됩니다."));
		}
		return bindingResult;

	}

	public void join(UserJoinRequest req) {
		userRepository.save(req.toEntity(encoder.encode(req.getPassword())));
	}

	public String login(String loginId, String password) {
		Optional<Users> user = userRepository.findByLoginId(loginId);
		if (user.isEmpty()) {
			return "해당 유저를 찾지못했습니다.";
		}
		if (encoder.matches(password, user.get().getPassword())) {
			return "로그인 성공!";
		}
		return "비밀번호가 일치하지 않습니다";

	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Users> user = userRepository.findByLoginId(username);
		if (user.isEmpty()) {
			throw new UsernameNotFoundException("해당 유저를 찾지 못했습니다.");
		}
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		if (user.get().isAdmin()) {
			authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		} else {
			authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		}
		return new User(user.get().getLoginId(), user.get().getPassword(), new ArrayList<>());
	}

	public Users findUserByLoginId(String loginId) {
		return userRepository.findByLoginId(loginId)
				.orElseThrow(() -> new UsernameNotFoundException("해당 유저를 찾지 못했습니다."));
	}

	public UserDTO getAuthenticatedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return null;
		}
		String username = authentication.getName();
		Optional<Users> userOptional = userRepository.findByLoginId(username);
		if (!userOptional.isPresent()) {
			return null;
		}

		Users user = userOptional.get();

		UserDTO userDto = new UserDTO();
		userDto.setLoginId(user.getLoginId());
		userDto.setName(user.getName());
		userDto.setNickname(user.getNickname());
		userDto.setGender(user.getGender());
		userDto.setAge(user.getAge());

		return userDto;
	}

}
