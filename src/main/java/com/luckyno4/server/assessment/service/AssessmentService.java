package com.luckyno4.server.assessment.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luckyno4.server.assessment.domain.Assessment;
import com.luckyno4.server.assessment.domain.AssessmentRepository;
import com.luckyno4.server.assessment.domain.AssessmentUser;
import com.luckyno4.server.assessment.domain.AssessmentUserRepository;
import com.luckyno4.server.assessment.dto.AssessmentRequest;
import com.luckyno4.server.assessment.dto.AssessmentResponse;
import com.luckyno4.server.category.domain.Category;
import com.luckyno4.server.category.domain.CategoryRepository;
import com.luckyno4.server.question.domain.Question;
import com.luckyno4.server.user.domain.User;
import com.luckyno4.server.user.domain.UserRepository;
import com.luckyno4.server.user.dto.UserRequests;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AssessmentService {
	private final AssessmentRepository assessmentRepository;
	private final CategoryRepository categoryRepository;
	private final UserRepository userRepository;
	private final AssessmentUserRepository assessmentUserRepository;

	@Transactional
	public Long save(User user, AssessmentRequest assessmentRequest) {
		Assessment assessment = assessmentRequest.toAssessment();
		assessment.setCreator(user);

		assessmentRepository.save(assessment);

		List<Category> categories = assessmentRequest.toCategories();

		categoryRepository.saveAll(categories);

		List<List<Question>> questions = assessmentRequest.toQuestions();

		for (int i = 0; i < categories.size(); i++) {
			setCategory(categories.get(i), questions.get(i));
		}

		setAssessment(assessment, categories);

		return assessment.getId();
	}

	private void setCategory(Category category, List<Question> questions) {
		questions.stream()
			.forEach(question -> question.setCategory(category));
	}

	private void setAssessment(Assessment assessment, List<Category> categories) {
		for (Category category : categories) {
			category.setAssessment(assessment);
		}
	}

	public void setRespondents(User user, Long id, UserRequests userRequests) {
		Assessment assessment = assessmentRepository.findById(id)
			.orElseThrow(EntityNotFoundException::new);

		validIsReadable(user, assessment);

		List<User> users = userRequests.getUserRequests()
			.stream()
			.map(userRequest ->
				userRepository.findByEmail(userRequest.getEmail())
					.orElse(User.builder()
						.email(userRequest.getEmail())
						.build())
			)
			.collect(Collectors.toList());

		List<AssessmentUser> assessmentUsers = users.stream()
			.map(respondent -> assessmentUserRepository.save(AssessmentUser.builder()
				.respond(respondent)
				.assessment(assessment)
				.build()))
			.collect(Collectors.toList());

		assessment.setAssessmentUsers(assessmentUsers);

		assessmentRepository.save(assessment);
	}

	@Transactional(readOnly = true)
	public AssessmentResponse read(User user, Long id) {
		Assessment assessment = assessmentRepository.findById(id)
			.orElseThrow(EntityNotFoundException::new);

		validIsReadable(user, assessment);

		return AssessmentResponse.of(assessment);
	}

	@Transactional(readOnly = true)
	public List<AssessmentResponse> readAll(User user) {
		List<Assessment> assessments = assessmentRepository.findAll();
		assessments.stream()
			.forEach(assessment -> validIsReadable(user, assessment));
		return AssessmentResponse.listOf(assessments);
	}

	private void validIsReadable(User user, Assessment assessment) {
		if (assessment.isNotReadable(user)) {
			throw new RuntimeException("접근할 수 없는 평가입니다.");
		}
	}
}
