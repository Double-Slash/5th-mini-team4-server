package com.luckyno4.server.user.service;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.luckyno4.server.security.TokenProvider;
import com.luckyno4.server.user.domain.AuthProvider;
import com.luckyno4.server.user.domain.Role;
import com.luckyno4.server.user.domain.User;
import com.luckyno4.server.user.domain.UserRepository;
import com.luckyno4.server.user.dto.AuthResponse;
import com.luckyno4.server.user.dto.LoginRequest;
import com.luckyno4.server.user.dto.SignUpRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	private final AuthenticationManager authenticationManager;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final TokenProvider tokenProvider;

	public AuthResponse authenticateUser(LoginRequest loginRequest) {
		Authentication authentication;
		try {
			authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
					loginRequest.getEmail(),
					loginRequest.getPassword()
				)
			);
		} catch (AuthenticationException ex) {
			throw new RuntimeException("Bad Login");
		}

		SecurityContextHolder.getContext().setAuthentication(authentication);

		return new AuthResponse(tokenProvider.createToken(authentication));
	}

	public Long registerUser(SignUpRequest signUpRequest) {
		Optional<User> byEmail = userRepository.findByEmail(signUpRequest.getEmail());
		if (byEmail.isPresent()) {
			if (byEmail.get().getProvider() != null) {
				throw new RuntimeException("Email Duplicated");
			}
			byEmail.ifPresent(user -> {
				user.setProvider(AuthProvider.local);
				user.setPassword(passwordEncoder.encode(user.getPassword()));
				user.setRole(Role.ROLE_USER);
			});
			return byEmail.get().getId();
		}
		User user = new User();
		user.setName(signUpRequest.getName());
		user.setEmail(signUpRequest.getEmail());
		user.setPassword(signUpRequest.getPassword());
		user.setProvider(AuthProvider.local);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRole(Role.ROLE_USER);
		User result = userRepository.save(user);

		return result.getId();
	}
}
