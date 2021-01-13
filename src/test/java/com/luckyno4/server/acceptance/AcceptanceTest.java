package com.luckyno4.server.acceptance;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import com.luckyno4.server.user.dto.AuthResponse;
import io.restassured.RestAssured;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = {"local"})
public abstract class AcceptanceTest {
	@LocalServerPort
	int port;

	@Autowired
	private UserCreate userCreate;

	@Autowired
	private DatabaseCleanUp databaseCleanup;

	public static String toHeaderValue(AuthResponse authResponse) {
		return authResponse.getTokenType() + " " + authResponse.getAccessToken();
	}

	@BeforeEach
	public void setUp() {
		RestAssured.port = port;
		databaseCleanup.execute();

		userCreate.execute();
	}
}

