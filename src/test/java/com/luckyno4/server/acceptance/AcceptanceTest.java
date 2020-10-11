package com.luckyno4.server.acceptance;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import io.restassured.RestAssured;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AcceptanceTest {
	@LocalServerPort
	int port;

	@Autowired
	private DatabaseCleanUp databaseCleanup;

	@BeforeEach
	public void setUp() {
		RestAssured.port = port;
		databaseCleanup.execute();
	}
}

