package com.luckyno4.server.answer.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luckyno4.server.answer.domain.Answer;
import com.luckyno4.server.answer.domain.AnswerRepository;
import com.luckyno4.server.answer.dto.AnswerRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnswerService {
	private final AnswerRepository answerRepository;

	@Transactional
	public void updateAnswer(Long id, AnswerRequest answerRequest) {
		Answer answer = answerRepository.findById(id).orElseThrow(EntityNotFoundException::new);
		Answer requestAnswer = answerRequest.toAnswer();

		answer.update(requestAnswer);
	}

	@Transactional
	public void deleteAnswer(Long id) {
		answerRepository.deleteById(id);
	}
}
