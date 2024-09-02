package com.example.demo.rule.executor;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Component;

@Component
public class RuleExecutor {

	public KieSession initializeRules() {

		KieServices kieServices = KieServices.Factory.get();
		KieContainer kieContainer = kieServices.getKieClasspathContainer();

		return kieContainer.newKieSession();
	}

	public void executeRule(KieSession kieSession) {
		kieSession.insert("John");
		kieSession.fireAllRules();
	}
}
