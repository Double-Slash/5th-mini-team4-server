package com.luckyno4.server.category.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotBlank;

import com.luckyno4.server.assessment.domain.Assessment;
import com.luckyno4.server.common.BaseTimeEntity;
import com.luckyno4.server.question.domain.Question;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Category extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_sequence_gen")
	@SequenceGenerator(name = "category_sequence_gen", sequenceName = "category_sequence")
	private Long id;

	@NotBlank
	private String category;

	@ManyToOne
	@JoinColumn(name = "assessment_id")
	private Assessment assessment;

	@OneToMany(mappedBy = "category", cascade = CascadeType.PERSIST, orphanRemoval = true)
	private List<Question> questions = new ArrayList<>();

	@Builder
	public Category(String category) {
		this.category = category;
	}

	public void setAssessment(Assessment assessment) {
		this.assessment = assessment;

		assessment.getCategories().add(this);
	}
}
