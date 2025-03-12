package com.sample;

import java.util.HashMap;
import java.util.Map;

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

        Map<String, String> resultMap = new HashMap<>();
        ksession.setGlobal("resultMap", resultMap);

        ksession.insert(new Person("John", 35));
        ksession.insert(new Person("Paul", 32));

        ksession.fireAllRules();

        System.out.println("resultMap = " + resultMap);

        ksession.dispose();

    }
}
