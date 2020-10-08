package com.luckyno4.server.question.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.luckyno4.server.question.domain.Question;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class QuestionRequest {
	@NotNull
	private boolean isDescription;

	@NotNull
	private boolean isContribution;

	@NotBlank
	private String question;

	@Builder
	public QuestionRequest(boolean isDescription, boolean isContribution, String question) {
		this.isDescription = isDescription;
		this.isContribution = isContribution;
		this.question = question;
	}

	public Question toQuestion() {
		return Question.builder()
			.isDescription(isDescription)
			.isContribution(isContribution)
			.question(question)
			.build();
	}
}
