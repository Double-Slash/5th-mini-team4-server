package com.luckyno4.server.category.dto;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.luckyno4.server.category.domain.Category;
import com.luckyno4.server.question.domain.Question;
import com.luckyno4.server.question.dto.QuestionRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class CategoryRequest {
	@NotBlank
	private String category;

	private List<@Valid QuestionRequest> questions;

	public Category toCategory() {
		List<Question> questionList = questions.stream()
			.map(QuestionRequest::toQuestion)
			.collect(Collectors.toList());

		return Category.builder()
			.category(this.category)
			.questions(questionList)
			.build();
	}
}
