package com.sample;

import java.util.ArrayList;
import java.util.List;

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
        List<String> resultList = new ArrayList<>();
        ksession.setGlobal("resultList", resultList);

        ksession.insert(new Person("John", 35));

        ksession.fireAllRules();

        System.out.println(resultList);

        ksession.dispose();

    }
}
