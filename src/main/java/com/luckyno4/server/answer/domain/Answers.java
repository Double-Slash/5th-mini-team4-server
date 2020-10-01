package com.luckyno4.server.answer.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Embeddable
public class Answers {
	@OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.EAGER)
	private Set<Answer> answers = new HashSet<>();

	public static Answers empty() {
		return new Answers();
	}

	public void addAnswer(Answer answer) {
		answers.add(answer);
	}

	public void deleteAnswer(Answer targetAnswer) {
		Set<Answer> deletedAnswers = answers.stream()
			.filter(answer -> !answer.getId().equals(targetAnswer.getId()))
			.collect(Collectors.toSet());
		answers.clear();
		answers.addAll(deletedAnswers);
	}
}
