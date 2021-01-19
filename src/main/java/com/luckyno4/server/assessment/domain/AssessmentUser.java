package com.luckyno4.server.assessment.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

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
@AllArgsConstructor
public class AssessmentUser {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "assessment_user_sequence_gen")
	@SequenceGenerator(name = "assessment_user_sequence_gen", sequenceName = "assessment_user_sequence")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "assessment_id")
	private Assessment assessment;

	@ManyToOne
	@JoinColumn(name = "respond_id")
	private User respond;

	@Builder
	public AssessmentUser(Assessment assessment, User respond) {
		this.assessment = assessment;
		this.respond = respond;
	}
}
