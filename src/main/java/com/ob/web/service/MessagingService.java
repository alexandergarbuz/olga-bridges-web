package com.ob.web.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ob.web.model.EmailMessage;
import com.ob.web.model.EmailResponse;

@Component
public class MessagingService {
	
	private static Logger LOG = LoggerFactory.getLogger(MessagingService.class);

	public boolean isPhoneValid(final String phoneNumber) {
        final String digitsOnly = phoneNumber.replaceAll("[^\\d]", "");
        return (StringUtils.length(digitsOnly) == 10);
	}
	public boolean isEmailValid(final String email) {
        final String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";
        final Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(email).matches();
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
		if(CollectionUtils.isNotEmpty(errors)) {
			status = EmailResponse.ERROR;
		}
		
		/*
		 * Add email sending here
		 */
			
		EmailResponse response = new EmailResponse(errors, status);
		LOG.debug("Validation results: {} ", response);		
		return response;
	}
	
}
