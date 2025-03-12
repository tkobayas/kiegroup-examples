package com.sample;

import org.junit.Test;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class DroolsTest {

    @Test
    public void testRules() {

        // With the default language level DRL6,
        // you will see the warning:
        // The use of a half constraint '|| < 10' is deprecated and will be removed in the future version (LanguageLevel.DRL10). Please add a left operand.

        // With language level DRL10,
        // you will see the error:
        // line 7:30 no viable alternative at input 'age > 20 || < 10'

        System.setProperty("drools.lang.level", "DRL10");

        KieServices ks = KieServices.Factory.get();
        KieContainer kcontainer = ks.getKieClasspathContainer();
        KieBase kbase = kcontainer.getKieBase();
        KieSession ksession = kbase.newKieSession();

        try {
            ksession.insert(new Person("John", 35));
            ksession.insert(new Person("Paul", 18));

            ksession.fireAllRules();
        } finally {
            ksession.dispose();
        }
    }
}
