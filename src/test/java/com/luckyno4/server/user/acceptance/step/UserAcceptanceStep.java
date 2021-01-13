package com.luckyno4.server.user.acceptance.step;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpHeaders.*;

import org.springframework.http.MediaType;

import com.luckyno4.server.user.domain.AuthProvider;
import com.luckyno4.server.user.domain.Role;
import com.luckyno4.server.user.dto.AuthResponse;
import com.luckyno4.server.user.dto.SignUpRequest;
import com.luckyno4.server.user.dto.UserResponse;
import com.luckyno4.server.user.dto.UserUpdateRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class UserAcceptanceStep {
	public static ExtractableResponse<Response> requestToGetCurrentUser(AuthResponse authResponse) {
		return given().log().all()
			.header(AUTHORIZATION, authResponse.getTokenType() + " " + authResponse.getAccessToken())
			.when()
			.get("/api/user/me")
			.then().log().all()
			.extract();
	}

	public static ExtractableResponse<Response> requestToUpdateUser(AuthResponse authResponse,
		UserUpdateRequest userUpdateRequest) {
		return given().log().all()
			.header(AUTHORIZATION, authResponse.getTokenType() + " " + authResponse.getAccessToken())
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(userUpdateRequest)
			.when()
			.patch("/api/user/me")
			.then().log().all()
			.extract();
	}

	public static ExtractableResponse<Response> requestToDeleteCurrentUser(AuthResponse authResponse) {
		return given().log().all()
			.header(AUTHORIZATION, authResponse.getTokenType() + " " + authResponse.getAccessToken())
			.when()
			.delete("/api/user/me")
			.then().log().all()
			.extract();
	}

	public static void assertThatGetCurrentUserSuccess(ExtractableResponse<Response> response,
		SignUpRequest signUpRequest) {
		UserResponse userResponse = response.as(UserResponse.class);

		assertAll(
			() -> assertThat(userResponse.getEmail()).isEqualTo(signUpRequest.getEmail()),
			() -> assertThat(userResponse.getId()).isNotNull(),
			() -> assertThat(userResponse.getName()).isEqualTo(signUpRequest.getName()),
			() -> assertThat(userResponse.getProvider()).isEqualTo(AuthProvider.local.toString()),
			() -> assertThat(userResponse.getRole()).isEqualTo(Role.ROLE_USER.toString())
		);
	}

}
