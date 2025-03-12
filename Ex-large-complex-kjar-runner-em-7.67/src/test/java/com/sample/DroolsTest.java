package com.sample;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class DroolsTest {

    @Test
    public void testKjar() {
        KieServices ks = KieServices.Factory.get();
        ReleaseId releaseId = ks.newReleaseId("com.sample", "large-complex-kjar-em-767", "1.0.0");

        long startKContainer = System.currentTimeMillis();
        KieContainer kContainer = ks.newKieContainer(releaseId);
        System.out.println("kContainer : " + (System.currentTimeMillis() - startKContainer) + "ms");

        long startKBase = System.currentTimeMillis();
        KieBase kBase = kContainer.getKieBase();
        System.out.println("kBase : " + (System.currentTimeMillis() - startKBase) + "ms");

        long startKSession = System.currentTimeMillis();
        KieSession kSession = kContainer.newKieSession();
        System.out.println("kSession : " + (System.currentTimeMillis() - startKSession) + "ms");

        List<String> resultList = new ArrayList<>();
        kSession.setGlobal("resultList", resultList);

        try {
            kSession.insert(new FactA("1", "2", "3", "4", "5"));
            kSession.insert(new FactB("1", "2", "3", "4", "5"));
            kSession.insert(new FactC("1", "2", "3", "4", "5"));
            kSession.insert(new FactD("1", "2", "3", "4", "5"));
            kSession.insert(new FactE("1", "2", "3", "4", "5"));

            kSession.fireAllRules();
        } finally {
            kSession.dispose();
        }

        System.out.println(resultList);
    }
}
