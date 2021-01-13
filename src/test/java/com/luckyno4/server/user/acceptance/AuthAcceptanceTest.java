package com.luckyno4.server.user.acceptance;

import static com.luckyno4.server.user.acceptance.step.AuthAcceptanceStep.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.luckyno4.server.acceptance.AcceptanceTest;
import com.luckyno4.server.user.dto.LoginRequest;
import com.luckyno4.server.user.dto.SignUpRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@DisplayName("Auth 인수/통합 테스트")
public class AuthAcceptanceTest extends AcceptanceTest {

	public static SignUpRequest signUpRequest;
	public static LoginRequest loginRequest;

	@Override
	@BeforeEach
	public void setUp() {
		super.setUp();

		// given
		signUpRequest = new SignUpRequest("test", "test@gmail.com", "test1234");
		loginRequest = new LoginRequest("test@gmail.com", "test1234");
	}

	@DisplayName("회원가입 요청을 한다.")
	@Test
	public void signUp() {
		// when
		ExtractableResponse<Response> response = requestSignUp(signUpRequest);

		// then
		assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
	}

	@DisplayName("로그인 요청을 한다.")
	@Test
	public void login() {
		// given
		requestSignUp(signUpRequest);

		// when
		ExtractableResponse<Response> response = requestLogin(loginRequest);

		// then
		assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
	}
}
