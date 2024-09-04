package com.example.demo.rule.executor;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.rule.domain.Person;
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

	public List<String> executeRule(KieSession kieSession, Person person) {
		List<String> results = new ArrayList<>();
		kieSession.setGlobal("results", results);
		kieSession.insert(person);
		kieSession.fireAllRules();
		return results;
	}
}
