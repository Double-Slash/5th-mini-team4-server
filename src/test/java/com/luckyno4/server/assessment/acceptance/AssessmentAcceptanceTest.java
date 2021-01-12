package com.luckyno4.server.assessment.acceptance;

import static org.assertj.core.api.Assertions.*;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.luckyno4.server.acceptance.AcceptanceTest;
import com.luckyno4.server.assessment.dto.AssessmentRequest;
import com.luckyno4.server.assessment.dto.AssessmentResponse;
import com.luckyno4.server.category.dto.CategoryRequest;
import com.luckyno4.server.question.domain.QuestionType;
import com.luckyno4.server.question.dto.QuestionRequest;
import io.restassured.RestAssured;

public class AssessmentAcceptanceTest extends AcceptanceTest {
	@Test
	void totalAssessmentTest() {
		// given
		// 평가 제작에 필요한 요소들
		QuestionRequest questionRequest = QuestionRequest.builder()
			.question("질문은 서술형과 점수형입니다.")
			.questionType(QuestionType.LONG)
			.build();

		CategoryRequest categoryRequest = new CategoryRequest("카테고리", Collections.singletonList(questionRequest));

		AssessmentRequest assessmentRequest = new AssessmentRequest("평가", Collections.singletonList(categoryRequest));

		// when
		// 평가 생성 API
		RestAssured.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.body(assessmentRequest)
			.when()
			.post("/api/assessments")
			.then().log().all()
			.statusCode(HttpStatus.CREATED.value());

		// then
		// 평가 전체 조회
		AssessmentResponse[] expect1 = RestAssured.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.body(assessmentRequest)
			.when()
			.get("/api/assessments")
			.then().log().all()
			.extract().as(AssessmentResponse[].class);

		assertThat(expect1[0].getAssessment()).isEqualTo(assessmentRequest.getAssessment());

		// 특정 평가 조회
		AssessmentResponse[] expect2 = RestAssured.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.body(assessmentRequest)
			.when()
			.get("/api/assessments")
			.then().log().all()
			.extract().as(AssessmentResponse[].class);

		assertThat(expect2[0].getAssessment()).isEqualTo(assessmentRequest.getAssessment());
	}
}
