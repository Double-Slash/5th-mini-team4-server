package com.luckyno4.server.answer.documentation;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;

public class AnswerDocumentation {
	public static RestDocumentationResultHandler updateAnswer() {
		return document("answer/update",
			pathParameters(
				parameterWithName("id").description("수정할 평가 ID")
			),
			requestFields(
				fieldWithPath("writer").type(JsonFieldType.STRING).description("사용자"),
				fieldWithPath("answer").type(JsonFieldType.STRING).description("평가 내용"),
				fieldWithPath("number").type(JsonFieldType.NUMBER).description("기여도")
			)
		);
	}

	public static RestDocumentationResultHandler deleteAnswer() {
		return document("answer/delete",
			pathParameters(
				parameterWithName("id").description("삭제할 평가 ID")
			));
	}

	public static RestDocumentationResultHandler createAnswer() {
		return document("answer/create",
			requestFields(
				fieldWithPath("assessmentId").type(JsonFieldType.NUMBER).description("평가 ID"),
				fieldWithPath("answers").type(JsonFieldType.ARRAY).description("답변 목록"),
				fieldWithPath("answers.[].writer").type(JsonFieldType.STRING).description("사용자"),
				fieldWithPath("answers.[].answer").type(JsonFieldType.STRING).description("평가 내용"),
				fieldWithPath("answers.[].number").type(JsonFieldType.NUMBER).description("기여도")
			));

	}
}
