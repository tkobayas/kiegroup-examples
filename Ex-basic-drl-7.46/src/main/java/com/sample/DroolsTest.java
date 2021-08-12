package com.sample;

import org.drools.core.reteoo.ReteDumper;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class DroolsTest {

    public static void main(String[] args) {

        System.setProperty("drools.dump.dir", "/home/tkobayas/tmp");
        
        KieServices ks = KieServices.Factory.get();
        KieContainer kcontainer = ks.getKieClasspathContainer();
        KieBase kbase = kcontainer.getKieBase();
        KieSession ksession = kbase.newKieSession();

        ReteDumper.dumpRete(ksession);
        
        ksession.insert(new Person("John", 35));
        ksession.insert(new Person("Paul", 33));

        ksession.fireAllRules();
        ksession.dispose();

    }
}
