package com.sample;

import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class DroolsTest {

    @Test
    public void testContainer() {
        try {
            KieServices ks = KieServices.Factory.get();
            ReleaseId releaseId = ks.newReleaseId("com.sample", "kjar-dep-parent", "1.0.0-SNAPSHOT");
            KieContainer kContainer = ks.newKieContainer(releaseId);
            KieSession kSession = kContainer.newKieSession();

            Person john = new Person("john", 20);
            kSession.insert(john);
            kSession.fireAllRules();

            kSession.dispose();

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

}
