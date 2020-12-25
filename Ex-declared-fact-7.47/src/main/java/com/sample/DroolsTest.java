package com.sample;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.definition.type.FactType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class DroolsTest {

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {

        KieServices ks = KieServices.Factory.get();
        KieContainer kcontainer = ks.getKieClasspathContainer();
        KieBase kbase = kcontainer.getKieBase();
        KieSession ksession = kbase.newKieSession();

        FactType factType = kbase.getFactType("com.sample", "SomeFactType");
        Object factInstance = factType.newInstance();
        System.out.println(factInstance);
        System.out.println(factType.getClass());

        ksession.insert(factInstance);

        ksession.fireAllRules();
        ksession.dispose();

    }
}
