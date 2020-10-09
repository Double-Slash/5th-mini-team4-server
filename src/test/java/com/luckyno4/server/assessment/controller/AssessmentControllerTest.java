package com.luckyno4.server.assessment.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luckyno4.server.answer.domain.Answer;
import com.luckyno4.server.answer.dto.AnswerRequest;
import com.luckyno4.server.assessment.documentation.AssessmentDocumentation;
import com.luckyno4.server.assessment.domain.Assessment;
import com.luckyno4.server.assessment.dto.AssessmentRequest;
import com.luckyno4.server.assessment.dto.AssessmentResponse;
import com.luckyno4.server.assessment.service.AssessmentService;
import com.luckyno4.server.category.domain.Category;
import com.luckyno4.server.category.dto.CategoryRequest;
import com.luckyno4.server.documentation.Documentation;
import com.luckyno4.server.question.domain.Question;
import com.luckyno4.server.question.dto.QuestionRequest;

@WebMvcTest(controllers = AssessmentController.class)
class AssessmentControllerTest extends Documentation {
	@MockBean
	private AssessmentService assessmentService;

	private AnswerRequest answerRequest;

	private AssessmentRequest assessmentRequest;

	private CategoryRequest categoryRequest;

	private QuestionRequest questionRequest;

	private Assessment assessment;

	private Category category;

	private AssessmentResponse assessmentResponse;

	private ObjectMapper objectMapper;

	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext,
		RestDocumentationContextProvider restDocumentationContextProvider) {
		super.setUp(webApplicationContext, restDocumentationContextProvider);

		answerRequest = new AnswerRequest("사용자", "답변입니다.", 100);

		questionRequest = QuestionRequest.builder()
			.isContribution(true)
			.isDescription(true)
			.question("질문은 서술형과 점수형입니다.")
			.build();

		categoryRequest = new CategoryRequest("카테고리", Collections.singletonList(questionRequest));

		assessmentRequest = new AssessmentRequest("평가", Collections.singletonList(categoryRequest));

		category = categoryRequest.toCategory();

		Question question = questionRequest.toQuestion();

		question.setCategory(category);

		Answer answer = answerRequest.toAnswer();

		answer.setQuestion(question);

		assessment = new Assessment(1L, "평가", Collections.singletonList(category));

		assessmentResponse = AssessmentResponse.of(assessment);

		objectMapper = new ObjectMapper();
	}

	@Test
	void createAssessment() throws Exception {
		given(assessmentService.save(any())).willReturn(1L);

		mockMvc.perform(post("/api/assessments")
			.content(objectMapper.writeValueAsString(assessmentRequest))
			.contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(status().isCreated())
			.andExpect(header().string("Location", "/api/assessments/1"))
			.andDo(print())
			.andDo(AssessmentDocumentation.createAssessment());
	}

	@Test
	void readAssessment() throws Exception {
		given(assessmentService.read(anyLong())).willReturn(assessmentResponse);

		mockMvc.perform(RestDocumentationRequestBuilders.get("/api/assessments/{id}", 1L)
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(print())
			.andDo(AssessmentDocumentation.readAssessment());
	}
}