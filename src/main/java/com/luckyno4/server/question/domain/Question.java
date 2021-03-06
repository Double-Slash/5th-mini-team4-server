package com.luckyno4.server.question.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotBlank;

import com.luckyno4.server.answer.domain.Answer;
import com.luckyno4.server.category.domain.Category;
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
public class Question extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "question_sequence_gen")
	@SequenceGenerator(name = "question_sequence_gen", sequenceName = "question_sequence")
	private Long id;

	@Enumerated(EnumType.STRING)
	private QuestionType questionType;

	@NotBlank
	private String question;

	@OneToMany(mappedBy = "question", cascade = CascadeType.PERSIST, orphanRemoval = true)
	private List<Answer> answers = new ArrayList<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	private Category category;

	@Builder
	public Question(String question, QuestionType questionType) {
		this.question = question;
		this.questionType = questionType;
	}

	public void setCategory(Category category) {
		this.category = category;

		category.getQuestions().add(this);
	}

	public void addAnswer(Answer answer) {
		answers.add(answer);
	}
}
