package com.luckyno4.server.assessment.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luckyno4.server.assessment.dto.AssessmentRequest;
import com.luckyno4.server.assessment.dto.AssessmentResponse;
import com.luckyno4.server.assessment.service.AssessmentService;
import lombok.RequiredArgsConstructor;

@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/assessments")
public class AssessmentController {
	private final AssessmentService assessmentService;

	@PostMapping
	public ResponseEntity<Void> createAssessment(@RequestBody @Valid AssessmentRequest assessmentRequest) {
		Long saveId = assessmentService.save(assessmentRequest);
		return ResponseEntity.created(URI.create("/api/assessments/" + saveId)).build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<AssessmentResponse> readAssessment(@PathVariable Long id) {
		return ResponseEntity.ok(assessmentService.read(id));
	}

	@GetMapping
	public ResponseEntity<List<AssessmentResponse>> readAllAssessment() {
		return ResponseEntity.ok(assessmentService.readAll());
	}
}
