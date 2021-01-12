package com.luckyno4.server.user.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luckyno4.server.user.dto.AuthResponse;
import com.luckyno4.server.user.dto.LoginRequest;
import com.luckyno4.server.user.dto.SignUpRequest;
import com.luckyno4.server.user.service.AuthService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class AuthController {
	private final AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		return ResponseEntity.ok(authService.authenticateUser(loginRequest));
	}

	@PostMapping("/signup")
	public ResponseEntity<Void> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
		Long userId = authService.registerUser(signUpRequest);
		return ResponseEntity.created(URI.create("/api/user/" + userId)).build();
	}
}