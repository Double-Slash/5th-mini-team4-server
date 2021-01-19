package com.luckyno4.server.assessment.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luckyno4.server.assessment.dto.AssessmentRequest;
import com.luckyno4.server.assessment.dto.AssessmentResponse;
import com.luckyno4.server.assessment.service.AssessmentService;
import com.luckyno4.server.security.CurrentUser;
import com.luckyno4.server.user.domain.User;
import com.luckyno4.server.user.dto.UserRequests;
import lombok.RequiredArgsConstructor;

@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/assessments")
public class AssessmentController {
	private final AssessmentService assessmentService;

	@PostMapping
	public ResponseEntity<Void> createAssessment(@CurrentUser User user,
		@RequestBody @Valid AssessmentRequest assessmentRequest) {
		Long saveId = assessmentService.save(user, assessmentRequest);
		return ResponseEntity.created(URI.create("/api/assessments/" + saveId)).build();
	}

	@PatchMapping("/{id}")
	public ResponseEntity<Void> setRespondents(@CurrentUser User user,
		@PathVariable Long id, UserRequests userRequests) {
		assessmentService.setRespondents(user, id, userRequests);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<AssessmentResponse> readAssessment(@CurrentUser User user, @PathVariable Long id) {
		return ResponseEntity.ok(assessmentService.read(user, id));
	}

	@GetMapping
	public ResponseEntity<List<AssessmentResponse>> readAllAssessment(@CurrentUser User user) {
		return ResponseEntity.ok(assessmentService.readAll(user));
	}
}
