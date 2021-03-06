package com.luckyno4.server.user.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.luckyno4.server.assessment.domain.Assessment;
import com.luckyno4.server.assessment.domain.AssessmentUser;
import com.luckyno4.server.category.domain.CategoryUser;
import com.luckyno4.server.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Getter
@Setter
@SQLDelete(sql = "UPDATE user SET deleted=true WHERE id=?")
@Where(clause = "deleted = false")
public class User extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence_gen")
	@SequenceGenerator(name = "user_sequence_gen", sequenceName = "user_sequence")
	private Long id;

	private String name;

	@Email
	@Column(nullable = false)
	private String email;

	private String imageUrl;

	private Boolean emailVerified = false;

	@JsonIgnore
	private String password;

	@Enumerated(EnumType.STRING)
	private AuthProvider provider;

	private String providerId;

	private Role role;

	private boolean deleted;

	@OneToMany(mappedBy = "creator", cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<Assessment> myAssessments = new ArrayList<>();

	@OneToMany(mappedBy = "respond", cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<AssessmentUser> assessmentUsers = new ArrayList<>();

	@OneToMany(mappedBy = "responsibility", cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<CategoryUser> categoryUsers = new ArrayList<>();

	@Builder
	public User(Long id, String name, @Email String email, String imageUrl, Boolean emailVerified,
		String password, @NotNull AuthProvider provider, String providerId, Role role, boolean deleted,
		List<Assessment> myAssessments,
		List<AssessmentUser> assessmentUsers,
		List<CategoryUser> categoryUsers) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.imageUrl = imageUrl;
		this.emailVerified = emailVerified;
		this.password = password;
		this.provider = provider;
		this.providerId = providerId;
		this.role = role;
		this.deleted = deleted;
		this.myAssessments = myAssessments;
		this.assessmentUsers = assessmentUsers;
		this.categoryUsers = categoryUsers;
	}

	public String roleName() {
		return role.name();
	}

	public boolean isReadable(String email) {
		return this.email.equals(email);
	}

	public void addAssessmentUser(AssessmentUser assessmentUser) {
		this.assessmentUsers.add(assessmentUser);
	}
}
