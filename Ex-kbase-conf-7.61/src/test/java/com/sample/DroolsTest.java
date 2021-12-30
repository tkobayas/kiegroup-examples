package com.sample;

import org.junit.Test;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.conf.EqualityBehaviorOption;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class DroolsTest {

    @Test
    public void testRules() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kcontainer = ks.getKieClasspathContainer();
        KieBaseConfiguration kbaseConf = ks.newKieBaseConfiguration();
        kbaseConf.setOption(EqualityBehaviorOption.EQUALITY);
        KieBase kbase = kcontainer.newKieBase(kbaseConf);
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
