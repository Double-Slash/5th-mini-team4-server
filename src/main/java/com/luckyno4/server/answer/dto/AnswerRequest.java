package com.luckyno4.server.answer.dto;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.luckyno4.server.answer.domain.Answer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class AnswerRequest {
	@Lob
	@NotBlank
	private String answer;

	@NotNull
	private int contribution;

	public Answer toAnswer() {
		return Answer.builder()
			.answer(answer)
			.contribution(contribution)
			.build();
	}
}
