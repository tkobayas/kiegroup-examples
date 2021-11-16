package com.sample;

import org.drools.modelcompiler.ExecutableModelProject;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;

import org.kie.api.KieBase;
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

        // KieFileSystem is responsible for gathering resources
        KieFileSystem kfs = ks.newKieFileSystem();

        // You can add your DRL files as InputStream here
        kfs.write("src/main/resources/com/sample/Sample1.drl",
                  ks.getResources().newInputStreamResource(new FileInputStream(new File("work/Sample1.drl"))));
        kfs.write("src/main/resources/com/sample/12345.drl",
                  ks.getResources().newInputStreamResource(new FileInputStream(new File("work/Sample2.drl"))));

        // You need to specify a unique ReleaseId per KieContainer (= the unit which you store a set of DRL files)
        // ReleaseId consists of "GroupId" + "ArtifactId" + "Version". The same idea of Maven artifact.
        ReleaseId releaseId = ks.newReleaseId("com.sample", "my-sample-a", "1.0.0");
        kfs.generateAndWritePomXML(releaseId);

        // Now resources are built and stored into an internal repository
        ks.newKieBuilder(kfs).buildAll(ExecutableModelProject.class);

        // You can get a KieContainer with the ReleaseId
        KieContainer kcontainer = ks.newKieContainer(releaseId);

        // KieContainer can create a KieBase
        KieBase kbase = kcontainer.getKieBase();

        // KieBase can create a KieSession
        KieSession ksession = kbase.newKieSession();

        try {
            ksession.insert(new Person("John", 35));
            ksession.fireAllRules();
        } finally {
            ksession.dispose();
        }

    }
}
