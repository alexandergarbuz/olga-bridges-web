package com.ob.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.ob.web.service.MessagingService;

@RestController
@RequestMapping("/email")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
public class MessagingController {
	
	private static final Logger LOG = LoggerFactory.getLogger(MessagingController.class);
	
	@Autowired
	private MessagingService messagingService;

	@PostMapping("/send")
	public ResponseEntity<EmailResponse> sendEmail(@RequestBody EmailMessage emailMessage) {
		LOG.debug("Received {}", emailMessage);
		
		EmailResponse response = messagingService.validateAndSend(emailMessage);
		
		LOG.debug("Returning {}", response);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
}
