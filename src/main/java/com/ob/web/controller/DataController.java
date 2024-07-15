package com.ob.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ob.web.model.Welcome;

@RestController
@RequestMapping("/data")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
public class DataController {
	
	private static final Logger LOG = LoggerFactory.getLogger(DataController.class);

	@GetMapping("/data")
	public ResponseEntity<Welcome> getData() {
		
		Welcome olga = new Welcome("Olga", "Bridges");
		
		LOG.debug("Returning {}", olga);
		
		return new ResponseEntity<>(olga, HttpStatus.OK);
	}
}
