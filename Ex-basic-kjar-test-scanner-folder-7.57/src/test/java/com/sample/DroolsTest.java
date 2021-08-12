package com.sample;

import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;

import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.KieScanner;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class DroolsTest {

    private static final String MY_KJARS_PATH = "/home/tkobayas/usr/git/tkobayas/kiegroup-examples/Ex-basic-kjar-test-scanner-folder-7.57/mykjars";

    @Test
    public void testContainer() {
        try {
            KieServices ks = KieServices.Factory.get();

            // Load the first kjar from filepath
            URL url = Paths.get(MY_KJARS_PATH + "/scan-kjar-1.0.0.jar").toUri().toURL();
            URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{url});

            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            Thread.currentThread().setContextClassLoader(urlClassLoader);
            try {
                ReleaseId firstReleaseId = ks.newReleaseId("com.sample", "scan-kjar", "1.0.0");
                KieModule kieModule = ks.getRepository().getKieModule(firstReleaseId);
                ks.getRepository().addKieModule(kieModule);
            } finally {
                Thread.currentThread().setContextClassLoader(cl);
            }

            ReleaseId releaseId = ks.newReleaseId("com.sample", "scan-kjar", "[1.0,2.0)");
            KieContainer kContainer = ks.newKieContainer(releaseId);
            KieScanner kScanner = ks.newKieScanner(kContainer, MY_KJARS_PATH);

            kScanner.start(10000L);

            for (int i = 0; i < 1000; i++) {
                KieSession kSession = kContainer.newKieSession();
                Person john = new Person("john", 20);
                kSession.insert(john);
                kSession.fireAllRules();
                kSession.dispose();
                Thread.sleep(1000L);
            }

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

}
