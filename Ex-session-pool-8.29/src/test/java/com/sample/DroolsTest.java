package com.sample;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieContainerSessionsPool;
import org.kie.api.runtime.KieSession;

public class DroolsTest {

    private static final int MAX_SESSION = 10;

    @Test
    public void testRules() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kcontainer = ks.getKieClasspathContainer();

        //        long start = System.currentTimeMillis();
        //        KieBase kbase = kcontainer.getKieBase();
        //        System.out.println("getKieBase : " + (System.currentTimeMillis() - start));

        long start = System.currentTimeMillis();
        KieContainerSessionsPool pool = kcontainer.newKieSessionsPool(10);
        System.out.println("newKieSessionPool : " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        KieSession tmpKsession = pool.newKieSession();
        tmpKsession.dispose();
        System.out.println("tmpKsession : pool.newKieSession : " + (System.currentTimeMillis() - start));

        List<KieSession> sessionList = new ArrayList<>();

        for (int i = 0; i < MAX_SESSION; i++) {

            start = System.currentTimeMillis();
            KieSession ksession = pool.newKieSession();
            System.out.println("pool.newKieSession : " + (System.currentTimeMillis() - start));

            sessionList.add(ksession);

            ksession.insert(new Person("John", 35));
            ksession.insert(new Person("Paul", 18));

            ksession.fireAllRules();
        }

        for (KieSession kieSession : sessionList) {
            kieSession.dispose();
        }

        pool.shutdown();
        kcontainer.dispose();

    }
}
