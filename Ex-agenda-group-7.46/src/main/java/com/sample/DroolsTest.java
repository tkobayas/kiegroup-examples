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

        ksession.insert(new Person("John", 35));
        ksession.insert(new Person("Paul", 33));

        System.out.println("--- calling fireAllRules()");
        ksession.fireAllRules();

        System.out.println("--- calling setFocus() for agenda1");
        ksession.getAgenda().getAgendaGroup("agenda1").setFocus();
        ksession.fireAllRules();

        System.out.println("--- calling setFocus() for agenda2");
        ksession.getAgenda().getAgendaGroup("agenda2").setFocus();
        ksession.fireAllRules();

        ksession.dispose();

    }
}
