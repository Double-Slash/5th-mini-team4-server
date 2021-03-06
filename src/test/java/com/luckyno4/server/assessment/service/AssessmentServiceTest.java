package com.luckyno4.server.assessment.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import javax.mail.MessagingException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.luckyno4.server.assessment.domain.Assessment;
import com.luckyno4.server.assessment.domain.AssessmentRepository;
import com.luckyno4.server.assessment.domain.AssessmentUser;
import com.luckyno4.server.assessment.domain.AssessmentUserRepository;
import com.luckyno4.server.assessment.dto.AssessmentRequest;
import com.luckyno4.server.assessment.dto.AssessmentResponse;
import com.luckyno4.server.category.domain.Category;
import com.luckyno4.server.category.domain.CategoryRepository;
import com.luckyno4.server.category.dto.CategoryRequest;
import com.luckyno4.server.common.mail.CustomMailSender;
import com.luckyno4.server.question.domain.QuestionType;
import com.luckyno4.server.question.dto.QuestionRequest;
import com.luckyno4.server.user.domain.User;
import com.luckyno4.server.user.domain.UserRepository;
import com.luckyno4.server.user.dto.UserRequest;
import com.luckyno4.server.user.dto.UserRequests;

@ExtendWith(MockitoExtension.class)
class AssessmentServiceTest {
	private AssessmentService assessmentService;

	@Mock
	private AssessmentRepository assessmentRepository;

	@Mock
	private CategoryRepository categoryRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private AssessmentUserRepository assessmentUserRepository;

	@Mock
	private CustomMailSender customMailSender;

	private AssessmentRequest assessmentRequest;

	private CategoryRequest categoryRequest;

	private QuestionRequest questionRequest;

	private Assessment assessment;

	private Category category;

	private AssessmentResponse assessmentResponse;

	private User user;

	private AssessmentUser assessmentUser;

	private UserRequests userRequests;

	@BeforeEach
	void setUp() {
		assessmentService = new AssessmentService(assessmentRepository, categoryRepository, userRepository,
			assessmentUserRepository, customMailSender);

		questionRequest = QuestionRequest.builder()
			.question("질문은 서술형과 점수형입니다.")
			.questionType(QuestionType.LONG)
			.build();

		categoryRequest = new CategoryRequest("카테고리", Collections.singletonList(questionRequest));

		assessmentRequest = new AssessmentRequest("평가", Collections.singletonList(categoryRequest));

		user = User.builder()
			.id(1L)
			.email("test@test.com")
			.name("test")
			.myAssessments(new ArrayList<>())
			.categoryUsers(new ArrayList<>())
			.assessmentUsers(new ArrayList<>())
			.build();

		category = categoryRequest.toCategory();
		assessment = new Assessment(
			1L, "평가", Collections.singletonList(category), user, new ArrayList<>());

		assessmentResponse = AssessmentResponse.of(assessment);

		assessmentUser = AssessmentUser.builder()
			.respond(user)
			.assessment(assessment)
			.build();

		UserRequest userRequest = new UserRequest("test@test.com");
		userRequests = new UserRequests(Collections.singletonList(userRequest));
	}

	@Test
	void save() {
		when(assessmentRepository.save(any())).thenReturn(assessment);

		assessmentService.save(user, assessmentRequest);

		verify(assessmentRepository).save(any());
	}

	@Test
	void setRespondents() throws MessagingException {
		when(assessmentRepository.findById(anyLong())).thenReturn(Optional.ofNullable(assessment));
		when(userRepository.findByEmail(any())).thenReturn(Optional.ofNullable(user));
		lenient().when(assessmentUserRepository.save(any())).thenReturn(assessmentUser);

		assessmentService.setRespondents(user, 1L, userRequests);

		verify(assessmentRepository).save(any());
	}

	@Test
	void read() {
		when(assessmentRepository.findById(anyLong())).thenReturn(Optional.ofNullable(assessment));

		AssessmentResponse expect = assessmentService.read(user, 1L);

		assertThat(expect).isEqualTo(assessmentResponse);
	}
}