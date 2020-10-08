package com.luckyno4.server.category.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.luckyno4.server.category.domain.Category;
import com.luckyno4.server.question.dto.QuestionResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class CategoryResponse {
	private String category;

	private List<QuestionResponse> questions;

	public static List<CategoryResponse> listOf(List<Category> categories) {
		if (categories.isEmpty()) {
			return new ArrayList<>();
		}
		return categories.stream()
			.map(CategoryResponse::of)
			.collect(Collectors.toList());
	}

	private static CategoryResponse of(Category category) {
		List<QuestionResponse> questionResponses = QuestionResponse.listOf(category.getQuestions());
		return new CategoryResponse(category.getCategory(), questionResponses);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof CategoryResponse))
			return false;
		CategoryResponse that = (CategoryResponse)o;
		return Objects.equals(category, that.category) &&
			Objects.equals(questions, that.questions);
	}

	@Override
	public int hashCode() {
		return Objects.hash(category, questions);
	}
}
