package com.luckyno4.server.user.controller;

import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luckyno4.server.common.WithMockCustomUser;
import com.luckyno4.server.documentation.Documentation;
import com.luckyno4.server.user.docs.UserDocumentation;
import com.luckyno4.server.user.domain.User;
import com.luckyno4.server.user.dto.UserUpdateRequest;
import com.luckyno4.server.user.service.UserService;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest extends Documentation {
	private final ObjectMapper objectMapper = new ObjectMapper();
	@MockBean
	private UserService userService;

	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext,
		RestDocumentationContextProvider restDocumentationContextProvider) {
		super.setUp(webApplicationContext, restDocumentationContextProvider);
		User user = User.builder()
			.id(1L)
			.name("toney")
			.build();
		when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user));

		MockMvcBuilders
			.webAppContextSetup(webApplicationContext)
			.apply(springSecurity())
			.build();
	}

	@DisplayName("사용자 정보를 수정한다.")
	@WithMockCustomUser
	@Test
	void updateCurrentUser() throws Exception {
		doNothing().when(userService).updateUser(any(), any());

		UserUpdateRequest userUpdateRequest = new UserUpdateRequest("작은곰");

		mockMvc.perform(patch("/api/user/me")
			.header("authorization", "Bearer ADMIN_TOKEN")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(userUpdateRequest)))
			.andExpect(status().isOk())
			.andDo(print())
			.andDo(UserDocumentation.updateUser());
	}

	@DisplayName("회원 탈퇴한다.")
	@WithMockCustomUser
	@Test
	void deleteCurrentUser() throws Exception {
		doNothing().when(userService).deleteCurrentUser(any());

		mockMvc.perform(delete("/api/user/me")
			.header("authorization", "Bearer ADMIN_TOKEN"))
			.andExpect(status().isNoContent())
			.andDo(print())
			.andDo(UserDocumentation.deleteMe());
	}
}