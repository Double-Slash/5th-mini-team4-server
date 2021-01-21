package com.luckyno4.server.common.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomMailSender {
	private final JavaMailSender javaMailSender;
	private final SpringTemplateEngine springTemplateEngine;

	public void sendMail(String assessment, String[] emails, String user, Long id) throws MessagingException {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setSubject(assessment);

		helper.setTo(emails);

		Context context = new Context();
		context.setVariable("assessment", assessment);
		context.setVariable("user", user + "님께서 평가 요청을 보냈습니다.");
		context.setVariable("id", "localhost:3000/respond/" + id);

		String html = springTemplateEngine.process("mail-template", context);
		helper.setText(html, true);

		javaMailSender.send(message);
	}
}
