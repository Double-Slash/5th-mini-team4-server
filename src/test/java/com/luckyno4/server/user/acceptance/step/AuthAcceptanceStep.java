package com.luckyno4.server.user.acceptance.step;

import static com.luckyno4.server.acceptance.UserCreate.*;
import static io.restassured.RestAssured.*;

import org.springframework.http.MediaType;

import com.luckyno4.server.user.dto.AuthResponse;
import com.luckyno4.server.user.dto.LoginRequest;
import com.luckyno4.server.user.dto.SignUpRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class AuthAcceptanceStep {

	public static ExtractableResponse<Response> requestSignUp(SignUpRequest signUpRequest) {
		return given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(signUpRequest)
			.when()
			.post("/api/auth/signup")
			.then().log().all()
			.extract();
	}

	public static ExtractableResponse<Response> requestLogin(LoginRequest loginRequest) {
		return given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(loginRequest)
			.when()
			.post("/api/auth/login")
			.then().log().all()
			.extract();
	}

	public static AuthResponse requestTokenByLogin(LoginRequest loginRequest) {
		return requestLogin(loginRequest).as(AuthResponse.class);
	}

	public static AuthResponse requestAdminAuth() {
		LoginRequest loginRequest = new LoginRequest(USER_EMAIL, USER_PASSWORD);

		return requestTokenByLogin(loginRequest);
	}

	public static String toHeaderValue(AuthResponse authResponse) {
		return authResponse.getTokenType() + " " + authResponse.getAccessToken();
	}
}
