package com.luckyno4.server.assessment.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotBlank;

import com.luckyno4.server.category.domain.Category;
import com.luckyno4.server.common.BaseTimeEntity;
import com.luckyno4.server.question.domain.Question;
import com.luckyno4.server.user.domain.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Assessment extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "assessment_sequence_gen")
	@SequenceGenerator(name = "assessment_sequence_gen", sequenceName = "assessment_sequence")
	private Long id;

	@NotBlank
	private String assessment;

	@OneToMany(mappedBy = "assessment", cascade = CascadeType.PERSIST, orphanRemoval = true)
	private List<Category> categories = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "creator_id")
	private User creator;

	@OneToMany(mappedBy = "assessment", cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<AssessmentUser> assessmentUsers = new ArrayList<>();

	@Builder
	public Assessment(String assessment) {
		this.assessment = assessment;
	}

	public boolean isNotReadable(User user) {
		boolean isNotRespond = assessmentUsers.stream()
			.map(AssessmentUser::getRespond)
			.noneMatch(respond -> respond.isReadable(user.getEmail()));
		return !creator.isReadable(user.getEmail()) && isNotRespond;
	}

	public void setCreator(User user) {
		this.creator = user;
		user.getMyAssessments().add(this);
	}

	public List<Question> getQuestions() {
		return categories.stream()
			.flatMap(category -> category.getQuestions().stream())
			.collect(Collectors.toList());
	}

	public void addAssessmentUser(AssessmentUser assessmentUser) {
		this.assessmentUsers.add(assessmentUser);
	}
}
