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
        ReleaseId releaseId = ks.newReleaseId("com.sample", "basic-kjar-em", "1.0.0");

        long startKContainer = System.currentTimeMillis();
        KieContainer kContainer = ks.newKieContainer(releaseId);
        System.out.println("kContainer : " + (System.currentTimeMillis() - startKContainer) + "ms");

        long startKBase = System.currentTimeMillis();
        KieBase kBase = kContainer.getKieBase();
        System.out.println("kBase : " + (System.currentTimeMillis() - startKBase) + "ms");

        long startKSession = System.currentTimeMillis();
        KieSession kSession = kContainer.newKieSession();
        System.out.println("kSession : " + (System.currentTimeMillis() - startKSession) + "ms");

        try {
            List<String> resultList = new ArrayList<>();
            kSession.setGlobal("resultList", resultList);
            Person john = new Person("john", 25);
            kSession.insert(john);
            kSession.fireAllRules();
            System.out.println(resultList);
        } finally {
            kSession.dispose();
        }
    }
}
