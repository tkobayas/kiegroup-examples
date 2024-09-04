package com.example.demo.rule.facade;

import java.util.List;

import com.example.demo.rule.domain.Person;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Component;

import com.example.demo.rule.executor.RuleExecutor;

@Component
public class RuleFacade {
	RuleExecutor re;

	public RuleFacade(RuleExecutor re) {
		this.re = re;
	}

	public List<String> executeRule(Person person) {
		KieSession ks = re.initializeRules();
		try {
			return re.executeRule(ks, person);
		} finally {
			ks.dispose();
		}
	}
}
