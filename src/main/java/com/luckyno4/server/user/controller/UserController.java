package com.luckyno4.server.user.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luckyno4.server.security.CurrentUser;
import com.luckyno4.server.user.domain.User;
import com.luckyno4.server.user.dto.UserResponse;
import com.luckyno4.server.user.dto.UserUpdateRequest;
import com.luckyno4.server.user.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/user")
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@RestController
public class UserController {
	private final UserService userService;

	@GetMapping("/me")
	public ResponseEntity<UserResponse> getCurrentUser(@CurrentUser User user) {
		return ResponseEntity.ok(UserResponse.of(user));
	}

	@PatchMapping("/me")
	public ResponseEntity<UserResponse> updateCurrentUser(@CurrentUser User user,
		@RequestBody @Valid UserUpdateRequest userRequest) {
		userService.updateUser(user, userRequest);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/me")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Void> deleteCurrentUser(@CurrentUser User user) {
		userService.deleteCurrentUser(user);

		return ResponseEntity.noContent().build();
	}
}
