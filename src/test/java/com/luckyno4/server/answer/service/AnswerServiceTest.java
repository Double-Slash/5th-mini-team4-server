package com.luckyno4.server.answer.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.luckyno4.server.answer.domain.Answer;
import com.luckyno4.server.answer.domain.AnswerRepository;
import com.luckyno4.server.answer.dto.AnswerRequest;

@ExtendWith(MockitoExtension.class)
class AnswerServiceTest {
	private AnswerService answerService;

	@Mock
	private AnswerRepository answerRepository;

	private Answer answer;

	private AnswerRequest answerRequest;

	@BeforeEach
	void setUp() {
		answerService = new AnswerService(answerRepository);

		answer = Answer.builder()
			.answer("그냥 그랬어요.")
			.contribution(40)
			.writer("사용자")
			.build();

		answerRequest = new AnswerRequest("아니에요 좋았어요", 80);
	}

	@Test
	void updateAnswer() {
		when(answerRepository.findById(anyLong())).thenReturn(Optional.of(answer));

		answerService.updateAnswer(1L, answerRequest);

		assertAll(
			() -> assertThat(answer.getAnswer()).isEqualTo(answerRequest.getAnswer()),
			() -> assertThat(answer.getContribution()).isEqualTo(answerRequest.getContribution())
		);
	}
}