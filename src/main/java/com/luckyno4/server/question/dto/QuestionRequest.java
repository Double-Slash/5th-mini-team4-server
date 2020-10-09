package com.luckyno4.server.question.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.luckyno4.server.question.domain.Question;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class QuestionRequest {
	@NotNull
	@JsonProperty(value = "description")
	private boolean isDescription;

	@NotNull
	@JsonProperty(value = "contribution")
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
