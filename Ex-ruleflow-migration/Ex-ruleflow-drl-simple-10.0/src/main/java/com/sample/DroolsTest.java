package com.sample;

import java.util.Collection;

import org.drools.core.common.InternalAgenda;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class DroolsTest {

    public static final void main(String[] args) {
        try {
            KieServices ks = KieServices.Factory.get();
            KieContainer kContainer = ks.getKieClasspathContainer();
            KieSession kSession = kContainer.newKieSession();

            Person person = new Person("John", 20);

            kSession.insert(person); // you can insert as many objects as you want
            ((InternalAgenda) kSession.getAgenda()).activateRuleFlowGroup("group1");
            kSession.fireAllRules();

            Collection<?> facts = kSession.getObjects();
            facts.forEach(System.out::println);

            kSession.dispose();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}