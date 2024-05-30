package com.knowget.knowgetbackend.domain.user.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.knowget.knowgetbackend.domain.user.SignInResponse;
import com.knowget.knowgetbackend.domain.user.dto.UserSignInDTO;
import com.knowget.knowgetbackend.domain.user.dto.UserSignUpDTO;
import com.knowget.knowgetbackend.domain.user.service.UserServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

	private final UserServiceImpl userServiceImpl;

	/**
	 * 사용자로부터 입력받은 ID의 중복여부 확인
	 *
	 * @param request 사용자 ID
	 * @return 중복 여부 (True or False)
	 * @author Jihwan
	 */
	@PostMapping("/check-username")
	public ResponseEntity<Boolean> checkUsername(@RequestBody Map<String, String> request) {
		String username = request.get("id");
		if (username == null) {
			return new ResponseEntity<>(true, HttpStatus.NO_CONTENT);
		}
		boolean exists = userServiceImpl.checkUsername(username);
		return new ResponseEntity<>(exists, HttpStatus.OK);
	}

	/**
	 * 사용자로부터 입력받은 값들로 회원가입을 진행
	 *
	 * @param userSignupDTO 사용자가 입력한 ID, 이름, 비밀번호, 전화번호, 이메일
	 * @return 회원가입 성공여부에 따른 결과 메시지
	 * @author Jihwan
	 */
	@PostMapping("/signup")
	public ResponseEntity<String> signUp(@RequestBody UserSignUpDTO userSignupDTO) {
		String msg = userServiceImpl.signUp(userSignupDTO);
		return new ResponseEntity<>(msg, HttpStatus.OK);
	}

	/**
	 * 사용자 로그인
	 *
	 * @param userSignInDTO 사용자가 입력한 ID, 비밀번호
	 * @return 로그인 성공여부에 따른 결과 메시지
	 * @author Jihwan
	 */
	@PostMapping("/signin")
	public ResponseEntity<SignInResponse> signIn(@RequestBody @Valid UserSignInDTO userSignInDTO) {
		SignInResponse msg = userServiceImpl.signIn(userSignInDTO);
		return new ResponseEntity<>(msg, HttpStatus.OK);
	}

	// @PostMapping("/logout")
	// public ResponseEntity<String> logout(HttpServletRequest request) {
	// 	String token = request.getHeader("Authorization");
	// 	generalMemberService.logout(token);
	// 	return new ResponseEntity<>("로그아웃 되었습니다.", HttpStatus.OK);
	// }

}
