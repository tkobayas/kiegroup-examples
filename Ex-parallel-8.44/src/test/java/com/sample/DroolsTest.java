package com.sample;

import org.drools.core.reteoo.ReteDumper;
import org.junit.Test;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class DroolsTest {

    @Test
    public void testRules() {

        System.setProperty("drools.parallelExecution", "parallel_evaluation");

        KieServices ks = KieServices.Factory.get();
        KieContainer kcontainer = ks.getKieClasspathContainer();
        KieBase kbase = kcontainer.getKieBase();
        KieSession ksession = kbase.newKieSession();

        ReteDumper.dumpRete(ksession);

        try {
            ksession.insert("GO");
            ksession.insert(new Person("John", 3));
            ksession.insert(new Person("Paul", 7));

            ksession.fireAllRules();
        } finally {
            ksession.dispose();
        }
    }
}
