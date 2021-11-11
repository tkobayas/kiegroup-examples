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
        
        ksession.addEventListener(new GlobalSetUpListener());

//        List<String> resultList = new ArrayList<>();
//        ksession.setGlobal("resultList", resultList);

        ksession.insert("reset");

        
        ksession.insert(new Person("John", 35));
        ksession.insert(new Person("Paul", 32));

        ksession.fireAllRules();

//        System.out.println("resultList = " + resultList);
        
        Object resultList = ksession.getGlobal("resultList");
        System.out.println("resultList = " + resultList);

        ksession.dispose();

    }
}
