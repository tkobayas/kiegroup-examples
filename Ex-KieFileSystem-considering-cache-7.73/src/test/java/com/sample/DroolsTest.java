package com.sample;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class DroolsTest {

    @Test
    public void testRules() throws Exception {
        // KieServices is the entry point of APIs
        KieServices ks = KieServices.Factory.get();

        // You need to specify a unique ReleaseId per KieContainer (= the unit which you store a set of DRL files)
        // ReleaseId consists of "GroupId" + "ArtifactId" + "Version". The same idea of Maven artifact.
        ReleaseId releaseId = ks.newReleaseId("com.sample", "my-sample-a", "1.0.0");

        buildResources(releaseId, new File("work/Sample1.drl"));

        // You can get a KieContainer with the ReleaseId
        KieContainer kcontainer = ks.newKieContainer(releaseId);

        System.out.println("=== 1st run");

        long start = System.currentTimeMillis();
        KieSession ksession1 = kcontainer.newKieSession();
        System.out.println("KieSession creation : elapsed time = " + (System.currentTimeMillis() - start) + "ms");

        try {
            ksession1.insert(new Person("John", 35));
            ksession1.fireAllRules();
        } finally {
            ksession1.dispose();
        }

        System.out.println("=== 2nd run");

        start = System.currentTimeMillis();
        KieSession ksession2 = kcontainer.newKieSession(); // you can reuse kcontainer for any subsequent ksession creation
        System.out.println("KieSession creation : elapsed time = " + (System.currentTimeMillis() - start) + "ms");

        try {
            ksession2.insert(new Person("Paul", 33));
            ksession2.fireAllRules();
        } finally {
            ksession2.dispose();
        }

        System.out.println("//=================================");

        // If you add or modify the resources and build a new KieContainer, make sure that it has a new ReleaseId (usually, version increment).
        ReleaseId newReleaseId = ks.newReleaseId("com.sample", "my-sample-a", "1.0.1");

        buildResources(newReleaseId, new File("work/Sample1.drl"), new File("work/Sample2.drl"));

        KieContainer newKcontainer = ks.newKieContainer(newReleaseId);

        System.out.println("=== 3rd run");

        start = System.currentTimeMillis();
        KieSession ksession3 = newKcontainer.newKieSession();
        System.out.println("KieSession creation : elapsed time = " + (System.currentTimeMillis() - start) + "ms");

        try {
            ksession3.insert(new Person("George", 32));
            ksession3.fireAllRules();
        } finally {
            ksession3.dispose();
        }
    }

    private void buildResources(ReleaseId releaseId, File... files) {
        long start = System.currentTimeMillis();

        // KieServices is the entry point of APIs
        KieServices ks = KieServices.Factory.get();

        // KieFileSystem is responsible for gathering resources
        KieFileSystem kfs = ks.newKieFileSystem();

        for (File file : files) {
            // You can add your DRL files as InputStream here
            try {
                kfs.write("src/main/resources/com/sample/" + file.getName(),
                          ks.getResources().newInputStreamResource(new FileInputStream(file)));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        kfs.generateAndWritePomXML(releaseId);

        // Now resources are built and stored into an internal repository with the releaseId
        ks.newKieBuilder(kfs).buildAll();

        System.out.println("resource build : elapsed time = " + (System.currentTimeMillis() - start) + "ms");

        start = System.currentTimeMillis();

        // You can get a KieContainer with the ReleaseId
        KieContainer kcontainer = ks.newKieContainer(releaseId);

        // The first newKieSession creates and caches a KieBase internally. Also it does some minor internal initialization work.
        // It will reduce the time of subsequent newKieSession calls. You may want to do this if you want faster "first newKieSession in production".
        KieSession ksession = kcontainer.newKieSession();
        ksession.dispose();

        System.out.println("The first (fake) KieSession creation : elapsed time = " + (System.currentTimeMillis() - start) + "ms");
    }
}
