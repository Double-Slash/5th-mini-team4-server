package com.luckyno4.server.question.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.luckyno4.server.question.domain.Question;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class QuestionResponse {
	private String question;

	private boolean isDescription;

	private boolean isContribution;

	public static List<QuestionResponse> listOf(List<Question> questions) {
		if (questions.isEmpty()) {
			return new ArrayList<>();
		}
		return questions.stream()
			.map(QuestionResponse::of)
			.collect(Collectors.toList());
	}

	private static QuestionResponse of(Question question) {
		return new QuestionResponse(question.getQuestion(), question.isDescription(), question.isContribution());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof QuestionResponse))
			return false;
		QuestionResponse that = (QuestionResponse)o;
		return isDescription == that.isDescription &&
			isContribution == that.isContribution &&
			Objects.equals(question, that.question);
	}

	@Override
	public int hashCode() {
		return Objects.hash(question, isDescription, isContribution);
	}
}
