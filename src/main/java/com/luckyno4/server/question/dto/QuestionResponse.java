package com.luckyno4.server.question.dto;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.luckyno4.server.answer.dto.AnswerResponse;
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

	@JsonProperty(value = "description")
	private boolean isDescription;

	@JsonProperty(value = "contribution")
	private boolean isContribution;

	private List<AnswerResponse> answers;

	public static List<QuestionResponse> listOf(List<Question> questions) {
		return questions.stream()
			.map(QuestionResponse::of)
			.collect(Collectors.toList());
	}

	private static QuestionResponse of(Question question) {
		List<AnswerResponse> answerResponses = AnswerResponse.listOf(question.getAnswers());
		return new QuestionResponse(question.getQuestion(), question.isDescription(), question.isContribution(),
			answerResponses);
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
