package com.ob.web.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailConfig {

	@Value("${mail.host}")
	private String host;

	@Value("${mail.port}")
	private int port;

	@Value("${mail.username}")
	private String username;

	@Value("${mail.password}")
	private String password;

	@Value("${mail.smtp.auth}")
	private String smtpAuth;

	@Value("${mail.smtp.starttls.enable}")
	private String starttlsEnable;

	@Value("${mail.debug}")
	private String debug;
	
	@Value("${mail.recepient}")
	private String recepient;

	@Bean
	public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        
        mailSender.setHost(host);
        mailSender.setPort(port);

        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", smtpAuth);
        props.put("mail.smtp.starttls.enable", starttlsEnable);
        props.put("mail.debug", debug);

		// props.put("mail.smtp.ssl.enable", "true");

		return mailSender;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public String getUsername() {
		return username;
	}

	public String getSmtpAuth() {
		return smtpAuth;
	}

	public String getStarttlsEnable() {
		return starttlsEnable;
	}

	public String getDebug() {
		return debug;
	}

	public String getRecepient() {
		return recepient;
	}
	
}
