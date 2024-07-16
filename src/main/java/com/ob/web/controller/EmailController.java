package com.ob.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ob.web.model.EmailMessage;
import com.ob.web.model.EmailResponse;

@RestController
@RequestMapping("/email")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
public class EmailController {

	
	private static final Logger LOG = LoggerFactory.getLogger(EmailController.class);

	@PostMapping("/send")
	public ResponseEntity<EmailResponse> sendEmail(@RequestBody EmailMessage emailMessage) {
		LOG.debug("Received {}", emailMessage);
		
		List<String> errors = new ArrayList<>();
		errors.add("Invalid phone number");
		errors.add("Invalid email");
		String status = EmailResponse.ERROR;
		
		EmailResponse response = new EmailResponse(errors, status);
		
		LOG.debug("Returning {}", response);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
}
