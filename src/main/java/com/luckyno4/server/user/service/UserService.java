package com.luckyno4.server.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luckyno4.server.user.domain.User;
import com.luckyno4.server.user.domain.UserRepository;
import com.luckyno4.server.user.dto.UserUpdateRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@Service
public class UserService {
	private final UserRepository userRepository;

	@Transactional
	public void deleteCurrentUser(User user) {
		userRepository.deleteById(user.getId());
	}

	public void updateUser(User user, UserUpdateRequest userRequest) {
		user.setName(userRequest.getName());
		userRepository.save(user);
	}
}
