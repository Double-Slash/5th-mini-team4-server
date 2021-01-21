package com.luckyno4.server.common.mail;

import javax.mail.MessagingException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CustomMailSenderTest {

	@Autowired
	private CustomMailSender customMailSender;

	@Test
	void sendMail() throws MessagingException {
		String[] emails = new String[1];
		emails[0] = "taekwondo714@naver.com";
		customMailSender.sendMail("테스트", emails, "개발자", 1L);
	}
}