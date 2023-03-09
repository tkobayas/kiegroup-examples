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

    private KieFileSystem kfs;

    @Test
    public void testRules() throws Exception {
        // KieServices is the entry point of APIs
        KieServices ks = KieServices.Factory.get();

        // kfs will be shared across 1st and 2nd run
        kfs = ks.newKieFileSystem();

        // You need to specify a unique ReleaseId per KieContainer (= the unit which you store a set of DRL files)
        // ReleaseId consists of "GroupId" + "ArtifactId" + "Version". The same idea of Maven artifact.
        ReleaseId releaseId = ks.newReleaseId("com.sample", "my-sample-a", "1.0.0");

        buildInitialResources(releaseId, new File("work/Sample1.drl"), new File("work/Sample2.drl"));

        // You can get a KieContainer with the ReleaseId
        KieContainer kcontainer = ks.newKieContainer(releaseId);

        System.out.println("=== 1st run : " + releaseId);

        KieSession ksession1 = kcontainer.newKieSession();

        try {
            ksession1.insert(new Person("John", 35));
            ksession1.fireAllRules();
        } finally {
            ksession1.dispose();
        }

        System.out.println("//================== Update Sample1.drl");

        // If you add or modify the resources and build a new KieContainer, make sure that it has a new ReleaseId (usually, version increment).
        ReleaseId newReleaseId = ks.newReleaseId("com.sample", "my-sample-a", "1.0.1");

        // Update Sample1.drl, but keep other files (Sample2.drl)
        buildResourceByUpdatingRule(newReleaseId, "Sample1.drl", new File("work/Sample1_updated.drl"));

        KieContainer newKcontainer = ks.newKieContainer(newReleaseId);

        System.out.println("=== 2nd run : " + newReleaseId);

        KieSession ksession2 = newKcontainer.newKieSession();

        try {
            ksession2.insert(new Person("George", 32));
            ksession2.fireAllRules();
        } finally {
            ksession2.dispose();
        }
    }

    private void buildInitialResources(ReleaseId releaseId, File... files) {
        KieServices ks = KieServices.Factory.get();

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
    }

    private void buildResourceByUpdatingRule(ReleaseId newReleaseId, String updatedFileName, File newFile) {
        KieServices ks = KieServices.Factory.get();

        // Update a DRL file
        try {
            kfs.write("src/main/resources/com/sample/" + updatedFileName,
                      ks.getResources().newInputStreamResource(new FileInputStream(newFile)));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        kfs.generateAndWritePomXML(newReleaseId);

        // Now resources are built and stored into an internal repository with the releaseId
        ks.newKieBuilder(kfs).buildAll();
    }
}
