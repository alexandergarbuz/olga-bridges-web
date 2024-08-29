package com.ob.web.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
public class EmailSender {
	private static Logger LOG = LoggerFactory.getLogger(EmailSender.class);
	private static final String UTF_8 = "UTF-8";
	@Autowired
	private JavaMailSender emailSender;

	public void sendSimpleMessage(final String to, final String subject, final String text) {

		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("noreply@olgabridges.com");
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		emailSender.send(message);
	}

	public void sendEmail(final String to, final String subject, final String messageToSend) throws MessagingException {
		boolean isHtml = true;
		boolean isMultipart = true;

		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, isMultipart, UTF_8);

		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(messageToSend, isHtml);
        
		LOG.debug("Sending message to {} recepient with {} subject : {}", to, subject, messageToSend);
		
		emailSender.send(message);
	}
}
