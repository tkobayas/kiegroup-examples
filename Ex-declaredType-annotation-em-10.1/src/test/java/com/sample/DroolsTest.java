package com.sample;

import org.drools.model.codegen.ExecutableModelProject;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.util.Arrays;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.ReleaseId;
import org.kie.api.definition.type.FactType;
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

        // You need to specify a unique ReleaseId per KieContainer (= the unit which you store a set of DRL files)
        // ReleaseId consists of "GroupId" + "ArtifactId" + "Version". The same idea of Maven artifact.
        ReleaseId releaseId = ks.newReleaseId("com.sample", "my-sample-a", "1.0.0");
        kfs.generateAndWritePomXML(releaseId);

        // Now resources are built and stored into an internal repository
        KieBuilder kieBuilder = ks.newKieBuilder(kfs);
        kieBuilder.buildAll(ExecutableModelProject.class);

        System.out.println("----");
        System.out.println(kieBuilder.getResults());
        System.out.println("----");

        // You can get a KieContainer with the ReleaseId
        KieContainer kcontainer = ks.newKieContainer(releaseId);

        // KieContainer can create a KieBase
        KieBase kbase = kcontainer.getKieBase();

        // Check annotations
        System.out.println("----");
        FactType factType = kbase.getFactType("com.sample", "Foo");
        Class<?> factClass = factType.getFactClass();
        System.out.println("== Foo Fact Class ==");
        System.out.println(factClass.getName());
        System.out.println("    annotation -> " + Arrays.stream(factClass.getAnnotations()).toList());
        Field factField = factClass.getDeclaredField("bar");
        System.out.println("  bar : annotation ->  " + Arrays.toString(factField.getAnnotations()));
        System.out.println("----");

        // KieBase can create a KieSession
        KieSession ksession = kbase.newKieSession();

        try {
            ksession.insert("Hello");
            ksession.fireAllRules();
        } finally {
            ksession.dispose();
        }
    }
}
