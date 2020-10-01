package com.luckyno4.server.answer.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
import com.luckyno4.server.answer.dto.AnswerRequest;
import com.luckyno4.server.answer.service.AnswerService;
import com.luckyno4.server.documentation.Documentation;

@WebMvcTest(controllers = AnswerController.class)
class AnswerControllerTest extends Documentation {

	private ObjectMapper objectMapper;

	@MockBean
	private AnswerService answerService;

	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext,
		RestDocumentationContextProvider restDocumentationContextProvider) {
		super.setUp(webApplicationContext, restDocumentationContextProvider);

		objectMapper = new ObjectMapper();
	}

	@Test
	void updateAnswer() throws Exception {
		doNothing().when(answerService).updateAnswer(anyLong(), any());

		AnswerRequest answerRequest = new AnswerRequest("답변입니다.", 100);

		mockMvc.perform(RestDocumentationRequestBuilders.put("/api/answers/{id}", 1L)
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(answerRequest)))
			.andExpect(status().isOk())
			.andDo(print())
			.andDo(AnswerDocumentation.updateAnswer());
	}

	@Test
	void deleteAnswer() throws Exception {
		doNothing().when(answerService).deleteAnswer(anyLong());

		mockMvc.perform(RestDocumentationRequestBuilders.delete("/api/answers/{id}", 1L))
			.andExpect(status().isNoContent())
			.andDo(print())
			.andDo(AnswerDocumentation.deleteAnswer());
	}
}