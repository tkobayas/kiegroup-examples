package com.example.demo.controller;

import java.util.List;

import com.example.demo.rule.domain.Person;
import com.example.demo.rule.facade.RuleFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {

    Logger logger = LoggerFactory.getLogger(SimpleController.class);

	RuleFacade ruleFacade;

	public SimpleController(RuleFacade ruleFacade) {
		this.ruleFacade = ruleFacade;
	}

	@GetMapping("/hello")
	private String hello() {
		logger.info("Before rule");
		Person person = new Person("John", 25); // An example object. You may create a Person object from a request.
		List<String> results = ruleFacade.executeRule(person);
		logger.info("After rule");

		return results.toString();
	}
}
