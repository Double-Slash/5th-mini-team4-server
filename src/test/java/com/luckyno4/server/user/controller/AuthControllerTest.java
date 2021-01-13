package com.luckyno4.server.user.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luckyno4.server.documentation.Documentation;
import com.luckyno4.server.user.docs.AuthDocumentation;
import com.luckyno4.server.user.dto.AuthResponse;
import com.luckyno4.server.user.dto.LoginRequest;
import com.luckyno4.server.user.dto.SignUpRequest;
import com.luckyno4.server.user.service.AuthService;

@WebMvcTest(controllers = AuthController.class)
class AuthControllerTest extends Documentation {
	@MockBean
	private AuthService authService;

	private ObjectMapper objectMapper = new ObjectMapper();

	@DisplayName("로그인을 한다.")
	@Test
	void authenticateUser() throws Exception {
		LoginRequest loginRequest = new LoginRequest("a@email.com", "password");

		when(authService.authenticateUser(any())).thenReturn(new AuthResponse("AccessToken"));

		mockMvc.perform(post("/api/auth/login")
			.content(objectMapper.writeValueAsString(loginRequest))
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(print())
			.andDo(AuthDocumentation.login());
	}

	@DisplayName("회원가입을 한다.")
	@Test
	void registerUser() throws Exception {
		SignUpRequest signUpRequest = new SignUpRequest("아이디", "a@email.com", "password");

		when(authService.registerUser(any())).thenReturn(1L);

		mockMvc.perform(post("/api/auth/signup")
			.content(new ObjectMapper().writeValueAsBytes(signUpRequest))
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andDo(print())
			.andDo(AuthDocumentation.signup());
	}
}