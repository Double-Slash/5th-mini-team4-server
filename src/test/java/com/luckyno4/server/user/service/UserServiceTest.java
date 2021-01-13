package com.luckyno4.server.user.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.luckyno4.server.user.domain.AuthProvider;
import com.luckyno4.server.user.domain.Role;
import com.luckyno4.server.user.domain.User;
import com.luckyno4.server.user.domain.UserRepository;
import com.luckyno4.server.user.dto.UserUpdateRequest;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
	@Mock
	private UserRepository userRepository;

	private UserService userService;

	private User user;

	@BeforeEach
	void setUp() {
		userService = new UserService(userRepository);

		user = new User();
		user.setId(1L);
		user.setEmail("a@email.com");
		user.setEmailVerified(true);
		user.setImageUrl("image.com");
		user.setName("toney");
		user.setPassword("password");
		user.setProvider(AuthProvider.local);
		user.setRole(Role.ROLE_USER);
		user.setProviderId("local");
	}

	@DisplayName("사용자 이름을 수정한다.")
	@Test
	void updateUser() {
		UserUpdateRequest userUpdateRequest = new UserUpdateRequest("작은곰");
		userService.updateUser(user, userUpdateRequest);

		assertThat(user.getName()).isEqualTo(userUpdateRequest.getName());
	}

	@DisplayName("회원 탈퇴한다.")
	@Test
	void deleteCurrentUser() {
		userService.deleteCurrentUser(user);

		verify(userRepository).deleteById(anyLong());
	}
}
