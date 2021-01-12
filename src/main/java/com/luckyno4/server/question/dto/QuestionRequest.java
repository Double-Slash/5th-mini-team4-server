package com.luckyno4.server.question.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;

import com.luckyno4.server.question.domain.Question;
import com.luckyno4.server.question.domain.QuestionType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class QuestionRequest {
	@NotBlank
	private String question;

	@Enumerated(EnumType.STRING)
	private QuestionType questionType;

	@Builder
	public QuestionRequest(String question, QuestionType questionType) {
		this.question = question;
		this.questionType = questionType;
	}

	public Question toQuestion() {
		return Question.builder()
			.question(question)
			.questionType(questionType)
			.build();
	}
}
