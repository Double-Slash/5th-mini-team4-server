package com.luckyno4.server.answer.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luckyno4.server.answer.domain.Answer;
import com.luckyno4.server.answer.domain.AnswerRepository;
import com.luckyno4.server.answer.dto.AnswerCreateRequest;
import com.luckyno4.server.answer.dto.AnswerRequest;
import com.luckyno4.server.assessment.domain.Assessment;
import com.luckyno4.server.assessment.domain.AssessmentRepository;
import com.luckyno4.server.question.domain.Question;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnswerService {
	private final AnswerRepository answerRepository;
	private final AssessmentRepository assessmentRepository;

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

	@Transactional
	public void createAnswer(AnswerCreateRequest answerCreateRequest) {
		Long assessmentId = answerCreateRequest.getAssessmentId();
		List<AnswerRequest> answerRequests = answerCreateRequest.getAnswers();

		List<Answer> answers = answerRequests.stream()
			.map(answer -> answer.toAnswer())
			.collect(Collectors.toList());

		Assessment assessment = assessmentRepository.findById(assessmentId)
			.orElseThrow(EntityNotFoundException::new);

		List<Question> questions = assessment.getQuestions();

		for (int i = 0; i < questions.size(); i++) {
			answers.get(i).setQuestion(questions.get(i));
		}
	}
}
