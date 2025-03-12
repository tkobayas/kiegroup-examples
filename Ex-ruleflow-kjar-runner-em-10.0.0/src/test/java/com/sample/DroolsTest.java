package com.sample;

import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class DroolsTest {

    @Test
    public void testKjar() {
        KieServices ks = KieServices.Factory.get();
        ReleaseId releaseId = ks.newReleaseId("com.sample", "ruleflow-kjar-em", "1.0.0");
        KieContainer kContainer = ks.newKieContainer(releaseId);
        KieSession kSession = kContainer.newKieSession();

        try {
            Message message = new Message();
            kSession.insert(message);
            kSession.startProcess("com.sample.bpmn.hello.flow");
        } finally {
            kSession.dispose();
        }
    }
}
