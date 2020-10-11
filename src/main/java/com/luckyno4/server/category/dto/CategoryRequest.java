package com.luckyno4.server.category.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.luckyno4.server.category.domain.Category;
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

	private List<QuestionRequest> questions;

	public Category toCategory() {
		return Category.builder()
			.category(this.category)
			.build();
	}
}
