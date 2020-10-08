package com.luckyno4.server.assessment.dto;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.luckyno4.server.assessment.domain.Assessment;
import com.luckyno4.server.category.domain.Category;
import com.luckyno4.server.category.dto.CategoryRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class AssessmentRequest {
	@NotBlank
	private String assessment;

	private List<@Valid CategoryRequest> categories;

	public Assessment toAssessment() {
		return Assessment.builder()
			.assessment(assessment)
			.build();
	}

	public List<Category> toCategories() {
		return categories.stream()
			.map(category -> category.toCategory())
			.collect(Collectors.toList());
	}
}
