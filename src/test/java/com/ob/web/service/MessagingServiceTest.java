package com.ob.web.service;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ob.web.model.EmailMessage;
import com.ob.web.model.EmailResponse;

public class MessagingServiceTest {
	
	private MessagingService service;

	@BeforeEach
	public void setUp() throws Exception {
		this.service  = new MessagingService();
	}

	@Test
	public void validateAndSend() {
		
		EmailResponse response = service.validateAndSend(new EmailMessage("Alex", "alex.garbuz@garbuz.com", "555 555 5555", "message"));
		Assertions.assertEquals(EmailResponse.SUCCESS, response.getStatus());
		Assertions.assertTrue(CollectionUtils.isEmpty(response.getErrors()));
	}
	@Test
	public void validateAndSend_InvalidName() {
		EmailResponse response = service.validateAndSend(new EmailMessage("", "alex_garbuz@garbuz.com", "555-555-5555", "message"));
		Assertions.assertEquals(EmailResponse.ERROR, response.getStatus());
		List<String> errors = response.getErrors();
		Assertions.assertTrue(CollectionUtils.size(errors) == 1);
		Assertions.assertEquals("Name cannot be blank", errors.get(0));
	}
	@Test
	public void validateAndSend_InvalidPhone_Short() {
		EmailResponse response = service.validateAndSend(new EmailMessage("Alex", "alex@garbuz.com", "012 345 678", "message"));
		Assertions.assertEquals(EmailResponse.ERROR, response.getStatus());
		List<String> errors = response.getErrors();
		Assertions.assertTrue(CollectionUtils.size(errors) == 1);
		Assertions.assertEquals("Please use 10 digit phone number", errors.get(0));
	}
	@Test
	public void validateAndSend_InvalidPhone_Long() {
		EmailResponse response = service.validateAndSend(new EmailMessage("Alex", "alex@garbuz.com", "012 345 67891", "message"));
		Assertions.assertEquals(EmailResponse.ERROR, response.getStatus());
		List<String> errors = response.getErrors();
		Assertions.assertTrue(CollectionUtils.size(errors) == 1);
		Assertions.assertEquals("Please use 10 digit phone number", errors.get(0));
	}
	@Test
	public void validateAndSend_InvalidMessage() {
		EmailResponse response = service.validateAndSend(new EmailMessage("Alex", "alex@garbuz.com", "(555) 555 5555", ""));
		Assertions.assertEquals(EmailResponse.ERROR, response.getStatus());
		List<String> errors = response.getErrors();
		Assertions.assertTrue(CollectionUtils.size(errors) == 1);
		Assertions.assertEquals("Message cannot be blank", errors.get(0));
	}	
	@Test
	public void validateAndSend_InvalidEmail_NoAt() {
		EmailResponse response = service.validateAndSend(new EmailMessage("Alex", "alexgarbuz.com", "(555) 555 5555", "message"));
		Assertions.assertEquals(EmailResponse.ERROR, response.getStatus());
		List<String> errors = response.getErrors();
		Assertions.assertTrue(CollectionUtils.size(errors) == 1);
		Assertions.assertEquals("Please enter valid email", errors.get(0));
	}
	@Test
	public void validateAndSend_InvalidEmail_NoDot() {
		EmailResponse response = service.validateAndSend(new EmailMessage("Alex", "alex@garbuzcom", "(555) 555 5555", "message"));
		Assertions.assertEquals(EmailResponse.ERROR, response.getStatus());
		List<String> errors = response.getErrors();
		Assertions.assertTrue(CollectionUtils.size(errors) == 1);
		Assertions.assertEquals("Please enter valid email", errors.get(0));
	}
	@Test
	public void validateAndSend_InvalidEmail_NoTextAfterDot() {
		EmailResponse response = service.validateAndSend(new EmailMessage("Alex", "alex@garbuz.", "(555) 555 5555", "message"));
		Assertions.assertEquals(EmailResponse.ERROR, response.getStatus());
		List<String> errors = response.getErrors();
		Assertions.assertTrue(CollectionUtils.size(errors) == 1);
		Assertions.assertEquals("Please enter valid email", errors.get(0));
	}
	@Test
	public void validateAndSend_InvalidEmail_WrongExtention() {
		EmailResponse response = service.validateAndSend(new EmailMessage("Alex", "alex@garbuz.1123", "(555) 555 5555", "message"));
		Assertions.assertEquals(EmailResponse.ERROR, response.getStatus());
		List<String> errors = response.getErrors();
		Assertions.assertTrue(CollectionUtils.size(errors) == 1);
		Assertions.assertEquals("Please enter valid email", errors.get(0));
	}
}
