package com.luckyno4.server.user.domain;

import java.util.Collections;

public class EmptyUser extends User {
	public EmptyUser() {
		super(null, null, null, null, null, null, null, null, null, false, Collections.emptyList(),
			Collections.emptyList(), Collections.emptyList());
	}
}