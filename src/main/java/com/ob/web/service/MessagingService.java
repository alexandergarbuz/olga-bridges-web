package com.ob.web.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ob.web.config.EmailConfig;
import com.ob.web.model.EmailMessage;
import com.ob.web.model.EmailResponse;

import jakarta.mail.MessagingException;



@Component
public class MessagingService {
	
	private static Logger LOG = LoggerFactory.getLogger(MessagingService.class);
	
    private EmailSender emailSender;
    
    private EmailConfig emailConfig;
    
	public MessagingService(EmailSender emailSender, EmailConfig emailConfig) {
		this.emailSender = emailSender;
		this.emailConfig = emailConfig;
	}
	
	public EmailResponse validateAndSend(final EmailMessage emailMessage) {
		LOG.debug("Validating {} message", emailMessage);
		
		String status = EmailResponse.SUCCESS;
		List<String> errors = new ArrayList<>();
		
		if(StringUtils.isBlank(emailMessage.getName())) {
			errors.add("Name cannot be blank");
		}
		if(!isEmailValid(emailMessage.getEmail())) {
			errors.add("Please enter valid email");
		}
		if(!isPhoneValid(emailMessage.getPhoneNumber())) {
			errors.add("Please use 10 digit phone number");
		}
		if(StringUtils.isBlank(emailMessage.getMessage())) {
			errors.add("Message cannot be blank");
		}		
		if(hasErrors(errors)) {
			status = EmailResponse.ERROR;
		}

		if(!hasErrors(errors)) {
			try {
				emailSender.sendEmail(emailConfig.getRecepient(), "Information Request", buildMessage(emailMessage.getName(), emailMessage.getEmail(), emailMessage.getPhoneNumber(), emailMessage.getMessage()));
				emailSender.sendEmail(emailMessage.getEmail(), "Thank you for reaching out to us.", buildaAtoResponseMessage());
			} catch (MessagingException e) {
	            LOG.error("Error {}", e);
	            errors.add("Email service is unavailable.");
	            status = EmailResponse.ERROR;
			}			
		}
		
		EmailResponse response = new EmailResponse(errors, status);
		LOG.debug("Validation results: {} ", response);		
		return response;
	}
	private boolean isPhoneValid(final String phoneNumber) {
        final String digitsOnly = phoneNumber.replaceAll("[^\\d]", "");
        return (StringUtils.length(digitsOnly) == 10);
	}
	private boolean isEmailValid(final String email) {
        final String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";
        final Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(email).matches();
	}

	private boolean hasErrors(final List<String> errors) {
		return CollectionUtils.isNotEmpty(errors);
	}
	
	private String buildMessage(final String name, final String email, final String phone, final String message) {
        String html = "";
        html += "<h1>New Information request:</h1>";
        html += "<p>Name: " +  name + "</p>";
        html += "<p>Email: <a href=\"mailto:" +  email + "\" target=\"_blank\">" +  email + "</a></p>";
        html += "<p>Phone: " +  phone + "</p>";
        html += "<div>" +  message + "</div>";
        
        return html;
	}
	private String buildaAtoResponseMessage() {
        String autoResponseMessage = "";
        autoResponseMessage += "<p>Thank you for reaching out to us. We will get back to you shortly.</p>";
        autoResponseMessage += "<p>If you need urgent assistance - please call (864) 884 3333.</p>";
        autoResponseMessage += "---<br/>";
        autoResponseMessage += "<strong>Olga Bridges</strong><br/>";
        autoResponseMessage += "<br/>";
        autoResponseMessage += "<i>Success Properties</i><br/>";
        autoResponseMessage += "<a href=\"www.olgabridges.com\">www.olgabridges.com</a> | <a href=\"mailto:info@olgabridges.com\">info@olgabridges.com</a> | (864) 884 3333<br/>";
       return autoResponseMessage;
	}
}
