package com.luckyno4.server.answer.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.luckyno4.server.answer.domain.Answer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class AnswerResponse {
	private String writer;

	private String answer;

	private int number;

	public static List<AnswerResponse> listOf(List<Answer> answers) {
		return answers.stream()
			.map(AnswerResponse::of)
			.collect(Collectors.toList());
	}

	private static AnswerResponse of(Answer answer) {
		return new AnswerResponse(answer.getWriter(), answer.getAnswer(), answer.getNumber());
	}
}
