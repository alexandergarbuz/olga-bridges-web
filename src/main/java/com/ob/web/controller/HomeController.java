package com.ob.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ob.web.model.Hello;

@Controller
public class HomeController {
	
	private static final Logger LOG = LoggerFactory.getLogger(HomeController.class);

    @GetMapping("/")
    public ModelAndView home() {
    	
    	LOG.debug("Saying hello");
    	
    	Hello hello = new Hello();
    	hello.setMessage("Welcome from Hello object ");
    	
    	ModelAndView mv = new ModelAndView();
    	mv.addObject("model", hello);
    	mv.setViewName("index");

        return mv;
    }
}
