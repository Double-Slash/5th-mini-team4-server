package com.luckyno4.server.answer.dto;

import java.util.Objects;

import javax.persistence.Lob;

import com.luckyno4.server.answer.domain.Answer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class AnswerRequest {
	private String writer;
	@Lob
	private String answer;

	private int number;

	public Answer toAnswer() {
		return Answer.builder()
			.writer(writer)
			.answer(answer)
			.number(number)
			.build();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof AnswerRequest))
			return false;
		AnswerRequest that = (AnswerRequest)o;
		return number == that.number &&
			Objects.equals(writer, that.writer) &&
			Objects.equals(answer, that.answer);
	}

	@Override
	public int hashCode() {
		return Objects.hash(writer, answer, number);
	}
}
