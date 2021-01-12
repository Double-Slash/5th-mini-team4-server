package com.luckyno4.server.question.dto;

import java.util.List;
import java.util.stream.Collectors;

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
	private String questionType;
	private List<AnswerResponse> answers;

	public static List<QuestionResponse> listOf(List<Question> questions) {
		return questions.stream()
			.map(QuestionResponse::of)
			.collect(Collectors.toList());
	}

	private static QuestionResponse of(Question question) {
		List<AnswerResponse> answerResponses = AnswerResponse.listOf(question.getAnswers());
		return new QuestionResponse(question.getQuestion(), question.getQuestionType().toString(),
			answerResponses);
	}
}
