package com.sample;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class DroolsTest {

    public static void main(String[] args) {

        KieServices ks = KieServices.Factory.get();
        KieContainer kcontainer = ks.getKieClasspathContainer();
        KieBase kbase = kcontainer.getKieBase();
        KieSession ksession = kbase.newKieSession();

        ksession.setGlobal("$ageA", 10);
        ksession.setGlobal("$ageB", 20);
        ksession.setGlobal("$ageC", 30);
        
        ksession.insert(new Person("John", 20));
        ksession.insert(new Person("Paul", 30));

        ksession.fireAllRules();
        ksession.dispose();

    }
}
