package com.luckyno4.server.category.domain;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class CategoryUser {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_user_sequence_gen")
	@SequenceGenerator(name = "category_user_sequence_gen", sequenceName = "category_user_sequence")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@ManyToOne
	@JoinColumn(name = "responsibility_id")
	private User responsibility;
}
