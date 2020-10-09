package com.luckyno4.server.assessment.documentation;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;

public class AssessmentDocumentation {
	public static RestDocumentationResultHandler createAssessment() {
		return document("assessments/create",
			requestFields(
				fieldWithPath("assessment").type(JsonFieldType.STRING).description("평가 제목"),
				fieldWithPath("categories").type(JsonFieldType.ARRAY).description("카테고리"),
				fieldWithPath("categories.[].category").type(JsonFieldType.STRING).description("카테고리 제목"),
				fieldWithPath("categories.[].questions").type(JsonFieldType.ARRAY).description("질문 정보"),
				fieldWithPath("categories.[].questions.[].question").type(JsonFieldType.STRING).description("질문 제목"),
				fieldWithPath("categories.[].questions.[].description").type(JsonFieldType.BOOLEAN)
					.description("질문 서술형 여부"),
				fieldWithPath("categories.[].questions.[].contribution").type(JsonFieldType.BOOLEAN)
					.description("질문 점수형 여부")
			),
			responseHeaders(
				headerWithName("Location").description("생성된 평가 ID")
			)
		);
	}

	public static RestDocumentationResultHandler readAssessment() {
		return document("assessments/read",
			pathParameters(
				parameterWithName("id").description("조회할 평가 ID")
			),
			responseFields(
				fieldWithPath("id").type(JsonFieldType.NUMBER).description("평가 ID"),
				fieldWithPath("assessment").type(JsonFieldType.STRING).description("평가 제목"),
				fieldWithPath("categories").type(JsonFieldType.ARRAY).description("카테고리"),
				fieldWithPath("categories.[].category").type(JsonFieldType.STRING).description("카테고리 제목"),
				fieldWithPath("categories.[].questions").type(JsonFieldType.ARRAY).description("질문 정보"),
				fieldWithPath("categories.[].questions.[].question").type(JsonFieldType.STRING).description("질문 제목"),
				fieldWithPath("categories.[].questions.[].description").type(JsonFieldType.BOOLEAN)
					.description("질문 서술형 여부"),
				fieldWithPath("categories.[].questions.[].contribution").type(JsonFieldType.BOOLEAN)
					.description("질문 점수형 여부"),
				fieldWithPath("categories.[].questions.[].answers").type(JsonFieldType.ARRAY).description("질문에 대한 응답"),
				fieldWithPath("categories.[].questions.[].answers.[].writer").type(JsonFieldType.STRING)
					.description("사용자"),
				fieldWithPath("categories.[].questions.[].answers.[].answer").type(JsonFieldType.STRING)
					.description("평가 내용"),
				fieldWithPath("categories.[].questions.[].answers.[].contribution").type(JsonFieldType.NUMBER)
					.description("기여도")
			));
	}
}
