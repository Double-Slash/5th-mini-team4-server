package com.luckyno4.server.assessment.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.luckyno4.server.assessment.domain.Assessment;
import com.luckyno4.server.category.dto.CategoryResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class AssessmentResponse {
	private Long id;

	private String assessment;

	private List<CategoryResponse> categories;

	public static List<AssessmentResponse> listOf(List<Assessment> assessments) {
		if (assessments.isEmpty()) {
			return new ArrayList<>();
		}

		return assessments.stream()
			.map(AssessmentResponse::of)
			.collect(Collectors.toList());
	}

	public static AssessmentResponse of(Assessment assessment) {
		List<CategoryResponse> categoryResponses = CategoryResponse.listOf(assessment.getCategories());
		return new AssessmentResponse(assessment.getId(), assessment.getAssessment(), categoryResponses);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof AssessmentResponse))
			return false;
		AssessmentResponse that = (AssessmentResponse)o;
		return Objects.equals(assessment, that.assessment) &&
			Objects.equals(categories, that.categories);
	}

	@Override
	public int hashCode() {
		return Objects.hash(assessment, categories);
	}
}
