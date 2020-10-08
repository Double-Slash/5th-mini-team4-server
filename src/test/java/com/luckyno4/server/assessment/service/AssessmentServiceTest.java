package com.luckyno4.server.assessment.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.luckyno4.server.assessment.domain.Assessment;
import com.luckyno4.server.assessment.domain.AssessmentRepository;
import com.luckyno4.server.assessment.dto.AssessmentRequest;
import com.luckyno4.server.assessment.dto.AssessmentResponse;
import com.luckyno4.server.category.domain.Category;
import com.luckyno4.server.category.dto.CategoryRequest;
import com.luckyno4.server.question.dto.QuestionRequest;

@ExtendWith(MockitoExtension.class)
class AssessmentServiceTest {
	private AssessmentService assessmentService;

	@Mock
	private AssessmentRepository assessmentRepository;

	private AssessmentRequest assessmentRequest;

	private CategoryRequest categoryRequest;

	private QuestionRequest questionRequest;

	private Assessment assessment;

	private Category category;

	private AssessmentResponse assessmentResponse;

	@BeforeEach
	void setUp() {
		assessmentService = new AssessmentService(assessmentRepository);

		questionRequest = QuestionRequest.builder()
			.isContribution(true)
			.isDescription(true)
			.question("질문은 서술형과 점수형입니다.")
			.build();

		categoryRequest = new CategoryRequest("카테고리", Collections.singletonList(questionRequest));

		assessmentRequest = new AssessmentRequest("평가", Collections.singletonList(categoryRequest));

		category = categoryRequest.toCategory();
		assessment = new Assessment(1L, "평가", Collections.singletonList(category));

		assessmentResponse = AssessmentResponse.of(assessment);
	}

	@Test
	void save() {
		when(assessmentRepository.save(any())).thenReturn(assessment);

		assessmentService.save(assessmentRequest);

		verify(assessmentRepository).save(any());
	}

	@Test
	void read() {
		when(assessmentRepository.findById(anyLong())).thenReturn(Optional.ofNullable(assessment));

		AssessmentResponse expect = assessmentService.read(1L);

		assertThat(expect).isEqualTo(assessmentResponse);
	}
}