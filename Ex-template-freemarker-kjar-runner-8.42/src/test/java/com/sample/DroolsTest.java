package com.sample;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class DroolsTest {

    @Test
    public void testKjar() {
        KieServices ks = KieServices.Factory.get();
        ReleaseId releaseId = ks.newReleaseId("com.sample", "template-freemarker-example-kjar", "1.0.0-SNAPSHOT");
        KieContainer kContainer = ks.newKieContainer(releaseId);
        KieSession ksession = kContainer.newKieSession();

        try {
            ksession.insert(new Cheese("stilton"));
            ksession.insert(new Person("michael", 42));
            final List<String> list = new ArrayList<String>();
            ksession.setGlobal("list", list);

            ksession.fireAllRules();

            System.out.println("list = " + list.toString());

        } finally {
            ksession.dispose();
        }
    }
}
