package com.luckyno4.server.answer.controller;

import static org.mockito.Mockito.*;
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
import com.luckyno4.server.answer.documentation.AnswerDocumentation;
import com.luckyno4.server.answer.dto.AnswerCreateRequest;
import com.luckyno4.server.answer.dto.AnswerRequest;
import com.luckyno4.server.answer.service.AnswerService;
import com.luckyno4.server.common.WithMockCustomUser;
import com.luckyno4.server.documentation.Documentation;

@WebMvcTest(controllers = AnswerController.class)
class AnswerControllerTest extends Documentation {

	private ObjectMapper objectMapper;

	@MockBean
	private AnswerService answerService;

	private AnswerRequest answerRequest;

	private AnswerCreateRequest answerCreateRequest;

	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext,
		RestDocumentationContextProvider restDocumentationContextProvider) {
		super.setUp(webApplicationContext, restDocumentationContextProvider);

		answerRequest = new AnswerRequest("사용자", "답변입니다.", 100);

		answerCreateRequest = new AnswerCreateRequest(1L, Collections.singletonList(answerRequest));

		objectMapper = new ObjectMapper();
	}

	@WithMockCustomUser
	@Test
	void updateAnswer() throws Exception {
		doNothing().when(answerService).updateAnswer(anyLong(), any());

		mockMvc.perform(RestDocumentationRequestBuilders.put("/api/answers/{id}", 1L)
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(answerRequest)))
			.andExpect(status().isOk())
			.andDo(print())
			.andDo(AnswerDocumentation.updateAnswer());
	}

	@WithMockCustomUser
	@Test
	void deleteAnswer() throws Exception {
		doNothing().when(answerService).deleteAnswer(anyLong());

		mockMvc.perform(RestDocumentationRequestBuilders.delete("/api/answers/{id}", 1L))
			.andExpect(status().isNoContent())
			.andDo(print())
			.andDo(AnswerDocumentation.deleteAnswer());
	}

	@WithMockCustomUser
	@Test
	void createAnswer() throws Exception {
		doNothing().when(answerService).createAnswer(any());

		mockMvc.perform(RestDocumentationRequestBuilders.post("/api/answers")
			.content(objectMapper.writeValueAsString(answerCreateRequest))
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(print())
			.andDo(AnswerDocumentation.createAnswer());
	}
}