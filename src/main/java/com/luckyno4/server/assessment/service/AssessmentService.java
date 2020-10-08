package com.luckyno4.server.assessment.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.luckyno4.server.assessment.domain.Assessment;
import com.luckyno4.server.assessment.domain.AssessmentRepository;
import com.luckyno4.server.assessment.dto.AssessmentRequest;
import com.luckyno4.server.assessment.dto.AssessmentResponse;
import com.luckyno4.server.category.domain.Category;
import com.luckyno4.server.question.domain.Question;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AssessmentService {
	private final AssessmentRepository assessmentRepository;

	public Long save(AssessmentRequest assessmentRequest) {
		Assessment assessment = assessmentRequest.toAssessment();
		assessmentRepository.save(assessment);

		List<Category> categories = assessmentRequest.toCategories();

		setAssessment(assessment, categories);

		return assessment.getId();
	}

	private void setCategory(Category category, List<Question> questions) {
		for (Question question : questions) {
			question.setCategory(category);
		}
	}

	private void setAssessment(Assessment assessment, List<Category> categories) {
		for (Category category : categories) {
			category.setAssessment(assessment);
		}
	}

	public AssessmentResponse read(Long id) {
		Assessment assessment = assessmentRepository.findById(id).orElseThrow(EntityNotFoundException::new);
		return AssessmentResponse.of(assessment);
	}

	public List<AssessmentResponse> readAll() {
		List<Assessment> assessments = assessmentRepository.findAll();
		return AssessmentResponse.listOf(assessments);
	}
}
