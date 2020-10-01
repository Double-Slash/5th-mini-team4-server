package com.luckyno4.server.answer.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luckyno4.server.answer.dto.AnswerRequest;
import com.luckyno4.server.answer.service.AnswerService;
import lombok.RequiredArgsConstructor;

@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/answers")
public class AnswerController {
	private final AnswerService answerService;

	@PutMapping("/{id}")
	public ResponseEntity<Void> updateAnswer(@PathVariable Long id, @RequestBody @Valid AnswerRequest answerRequest) {
		answerService.updateAnswer(id, answerRequest);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteAnswer(@PathVariable Long id) {
		answerService.deleteAnswer(id);
		return ResponseEntity.noContent().build();
	}
}
