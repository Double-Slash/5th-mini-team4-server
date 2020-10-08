package com.luckyno4.server.answer.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.luckyno4.server.common.BaseTimeEntity;
import com.luckyno4.server.question.domain.Question;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

	@ManyToOne
	@JoinColumn(name = "question_id")
	private Question question;

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
