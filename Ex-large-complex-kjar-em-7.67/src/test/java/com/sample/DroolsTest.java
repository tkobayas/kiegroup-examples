package com.sample;

import java.util.ArrayList;
import java.util.List;

import org.drools.core.reteoo.ReteDumper;
import org.junit.Test;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class DroolsTest {

    @Test
    public void testRules() {
        KieServices ks = KieServices.Factory.get();
        long start = System.currentTimeMillis();
        KieContainer kcontainer = ks.getKieClasspathContainer();
        System.out.println("getKieClasspathContainer : " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        KieBase kbase = kcontainer.getKieBase();
        System.out.println("getKieBase : " + (System.currentTimeMillis() - start));

        KieSession ksession = kbase.newKieSession();
        List<String> resultList = new ArrayList<>();
        ksession.setGlobal("resultList", resultList);

//        ReteDumper.dumpRete(ksession);

        try {
            ksession.insert(new FactA("1", "2", "3", "4", "5"));
            ksession.insert(new FactB("1", "2", "3", "4", "5"));
            ksession.insert(new FactC("1", "2", "3", "4", "5"));
            ksession.insert(new FactD("1", "2", "3", "4", "5"));
            ksession.insert(new FactE("1", "2", "3", "4", "5"));

            ksession.fireAllRules();
        } finally {
            ksession.dispose();
        }

        System.out.println(resultList);
    }
}
