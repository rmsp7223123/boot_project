package service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import dto.UserJoinRequest;
import lombok.RequiredArgsConstructor;
import repository.UsersRepository;

@Service
@RequiredArgsConstructor
public class UserService {

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
        userRepository.save(req.toEntity( encoder.encode(req.getPassword()) ));
    }

}