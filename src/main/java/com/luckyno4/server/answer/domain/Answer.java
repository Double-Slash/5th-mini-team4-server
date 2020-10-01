package com.luckyno4.server.answer.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.luckyno4.server.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Answer extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "answer_sequence_gen")
	@SequenceGenerator(name = "answer_sequence_gen", sequenceName = "answer_sequence")
	private Long id;

	private String writer;

	@NotBlank
	private String answer;

	@NotNull
	private int contribution;

	@Builder
	public Answer(String writer, String answer, int contribution) {
		this.writer = writer;
		this.answer = answer;
		this.contribution = contribution;
	}

	public void update(Answer requestAnswer) {
		this.answer = requestAnswer.answer;
		this.contribution = requestAnswer.contribution;
	}
}
