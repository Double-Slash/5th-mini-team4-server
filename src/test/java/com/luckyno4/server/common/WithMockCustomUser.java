package com.luckyno4.server.common;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.security.test.context.support.WithSecurityContext;

import com.luckyno4.server.user.domain.AuthProvider;
import com.luckyno4.server.user.domain.Role;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser {
	long id() default 1L;

	String email() default "a@email.com";

	boolean emailVerified() default true;

	String imageUrl() default "image.com";

	String name() default "hi";

	String password() default "password";

	AuthProvider provider() default AuthProvider.local;

	Role roles() default Role.ROLE_USER;

	String providerId() default "providerID";
}
