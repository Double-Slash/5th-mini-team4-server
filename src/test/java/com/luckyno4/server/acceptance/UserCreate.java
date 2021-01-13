package com.luckyno4.server.acceptance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luckyno4.server.user.domain.AuthProvider;
import com.luckyno4.server.user.domain.Role;
import com.luckyno4.server.user.domain.User;
import com.luckyno4.server.user.domain.UserRepository;

@Service
@Profile("local")
public class UserCreate {
	public static final String USER_NAME = "test";
	public static final String USER_EMAIL = "test@test.com";
	public static final String USER_PASSWORD = "test1234";

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Transactional
	public void execute() {
		User user = new User();
		user.setName(USER_NAME);
		user.setEmail(USER_EMAIL);
		user.setPassword(USER_PASSWORD);
		user.setProvider(AuthProvider.local);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRole(Role.ROLE_USER);

		userRepository.save(user);
	}
}
