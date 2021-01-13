package com.luckyno4.server.user.acceptance;

import static com.luckyno4.server.user.acceptance.step.AuthAcceptanceStep.*;
import static com.luckyno4.server.user.acceptance.step.UserAcceptanceStep.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.luckyno4.server.acceptance.AcceptanceTest;
import com.luckyno4.server.user.dto.AuthResponse;
import com.luckyno4.server.user.dto.LoginRequest;
import com.luckyno4.server.user.dto.SignUpRequest;
import com.luckyno4.server.user.dto.UserUpdateRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@DisplayName("User 인수/통합 테스트")
public class UserAcceptanceTest extends AcceptanceTest {

    public static SignUpRequest signUpRequest;
    public static LoginRequest loginRequest;
    public static AuthResponse authResponse;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        // given
        signUpRequest = new SignUpRequest("test", "test@gmail.com", "test1234");
        requestSignUp(signUpRequest);

        loginRequest = new LoginRequest("test@gmail.com", "test1234");
        authResponse = requestTokenByLogin(loginRequest);
    }

    @DisplayName("현재 유저의 정보를 조회한다.")
    @Test
    void getCurrentUser() {

        // when
        ExtractableResponse<Response> response = requestToGetCurrentUser(authResponse);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThatGetCurrentUserSuccess(response, signUpRequest);
    }

    @DisplayName("현재 유저의 정보를 수정한다.")
    @Test
    void updateUser() {
        //given
        UserUpdateRequest userUpdateRequest = new UserUpdateRequest("작은곰");

        // when
        ExtractableResponse<Response> updateResponse = requestToUpdateUser(authResponse, userUpdateRequest);

        // then
        assertThat(updateResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("현재 유저가 탈퇴한다.")
    @Test
    void deleteUser() {

        // when
        ExtractableResponse<Response> response = requestToDeleteCurrentUser(authResponse);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("현재 유저가 탈퇴 후, 동일한 이메일로 회원가입한다.")
    @Test
    void deleteUserSignUp() {

        // when
        ExtractableResponse<Response> response = requestToDeleteCurrentUser(authResponse);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());

        // given
        signUpRequest = new SignUpRequest("test", "test@test.com", "test1234");

        loginRequest = new LoginRequest("test@test.com", "test1234");

        requestSignUp(signUpRequest);
        AuthResponse reAuthResponse = requestTokenByLogin(loginRequest);

        // when
        ExtractableResponse<Response> getUserResponse = requestToGetCurrentUser(reAuthResponse);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
        assertThatGetCurrentUserSuccess(getUserResponse, signUpRequest);
    }
}
