package com.sample;

import org.drools.core.reteoo.ReteDumper;
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

        ReteDumper.dumpRete(kbase);

        Parameter p1 = new Parameter();
        p1.getList().add("ABC");
        p1.getList().add("DEF");
        
        Parameter p2 = new Parameter();
        p2.setItem("ABC");

        ksession.insert(p1);
        ksession.insert(p2);

        ksession.fireAllRules();
        ksession.dispose();

    }
}
