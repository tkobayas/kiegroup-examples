package com.example.demo.rule.facade;

import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Component;

import com.example.demo.rule.executor.RuleExecutor;

@Component
public class RuleFacade {
	RuleExecutor re;

	public RuleFacade(RuleExecutor re) {
		this.re = re;
	}

	public void executeRule() {
		KieSession ks = re.initializeRules();
		try {
			re.executeRule(ks);
		} finally {
			ks.dispose();
		}
	}
}
