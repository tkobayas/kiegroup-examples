package com.sample;

import java.util.Collection;

import org.junit.Test;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.definition.rule.Rule;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class DroolsTest {

    @Test
    public void testRules() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kcontainer = ks.getKieClasspathContainer();
        KieBase kbase = kcontainer.getKieBase();
        KieSession ksession = kbase.newKieSession();
        
        kbase.getKiePackages().forEach(pkg -> {
            Collection<Rule> rules = pkg.getRules();
            System.out.println("rules.size() = " + rules.size());
        });

        try {
            ksession.insert(new Person("John", 35));
            ksession.insert(new Person("Paul", 18));

            ksession.fireAllRules();
            
            
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
        } finally {
            ksession.dispose();
        }
    }
}
