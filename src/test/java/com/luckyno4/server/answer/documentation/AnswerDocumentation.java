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
				fieldWithPath("answer").type(JsonFieldType.STRING).description("평가 내용"),
				fieldWithPath("contribution").type(JsonFieldType.NUMBER).description("기여도")
			)
		);
	}

	public static RestDocumentationResultHandler deleteAnswer() {
		return document("answer/delete",
			pathParameters(
				parameterWithName("id").description("삭제할 평가 ID")
			));
	}
}
