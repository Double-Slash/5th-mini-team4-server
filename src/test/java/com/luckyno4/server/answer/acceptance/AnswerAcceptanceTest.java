package com.luckyno4.server.answer.acceptance;

import static com.luckyno4.server.user.acceptance.step.AuthAcceptanceStep.*;
import static org.apache.http.HttpHeaders.*;
import static org.assertj.core.api.Assertions.*;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.luckyno4.server.acceptance.AcceptanceTest;
import com.luckyno4.server.answer.dto.AnswerCreateRequest;
import com.luckyno4.server.answer.dto.AnswerRequest;
import com.luckyno4.server.assessment.dto.AssessmentRequest;
import com.luckyno4.server.assessment.dto.AssessmentResponse;
import com.luckyno4.server.category.dto.CategoryRequest;
import com.luckyno4.server.question.domain.QuestionType;
import com.luckyno4.server.question.dto.QuestionRequest;
import com.luckyno4.server.user.dto.AuthResponse;
import io.restassured.RestAssured;

public class AnswerAcceptanceTest extends AcceptanceTest {
	@Test
	void totalAnswerTest() {
		// given
		// 평가 생성 및 응답 생성에 필요한 요소
		QuestionRequest questionRequest = QuestionRequest.builder()
			.question("질문은 서술형과 점수형입니다.")
			.questionType(QuestionType.LONG)
			.build();

		CategoryRequest categoryRequest = new CategoryRequest("카테고리", Collections.singletonList(questionRequest));

		AssessmentRequest assessmentRequest = new AssessmentRequest("평가", Collections.singletonList(categoryRequest));

		AuthResponse authResponse = requestAdminAuth();

		RestAssured.given().log().all()
			.header(AUTHORIZATION, toHeaderValue(authResponse))
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.body(assessmentRequest)
			.when()
			.post("/api/assessments")
			.then().log().all()
			.statusCode(HttpStatus.CREATED.value());

		AnswerRequest answerRequest = new AnswerRequest("작성자", "서술형 응답 내역", 50);

		AnswerCreateRequest answerCreateRequest = new AnswerCreateRequest(1L, Collections.singletonList(answerRequest));

		// when
		// 응답 생성 API 호출
		RestAssured.given().log().all()
			.header(AUTHORIZATION, toHeaderValue(authResponse))
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.body(answerCreateRequest)
			.when()
			.post("/api/answers")
			.then().log().all()
			.statusCode(HttpStatus.OK.value());

		// then
		// 평가 호출 시 질문에 대한 응답이 옳게 저장되었는가.
		AssessmentResponse expect = RestAssured.given().log().all()
			.header(AUTHORIZATION, toHeaderValue(authResponse))
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.when()
			.get("/api/assessments/1")
			.then().log().all()
			.extract().as(AssessmentResponse.class);

		assertThat(expect.getCategories().get(0).getQuestions().get(0).getAnswers().get(0).getWriter()).isEqualTo(
			answerRequest.getWriter());
		assertThat(expect.getCategories().get(0).getQuestions().get(0).getAnswers().get(0).getAnswer()).isEqualTo(
			answerRequest.getAnswer());
		assertThat(expect.getCategories().get(0).getQuestions().get(0).getAnswers().get(0).getContribution()).isEqualTo(
			answerRequest.getContribution());
	}
}
