package com.sample;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.drools.model.codegen.ExecutableModelProject;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class DroolsTest {

    private final KieServices ks = KieServices.Factory.get();
    private final ReleaseId releaseIdCustomerA1 = ks.newReleaseId("com.sample", "my-sample-customer-a", "1.0.0");
    private final ReleaseId releaseIdCustomerB1 = ks.newReleaseId("com.sample", "my-sample-customer-b", "1.0.0");
    private final ReleaseId releaseIdCustomerA2 = ks.newReleaseId("com.sample", "my-sample-customer-a", "2.0.0");

    @Test
    public void differentArtifactIdsAndVersions() throws Exception {
        System.out.println("===== differentArtifactIdsAndVersions");
        System.out.println("--- Create CustomerA version 1 KieContainer");
        List<File> fileListA1 = new ArrayList<>();
        fileListA1.add(new File("work/CommonRule-1.drl"));
        fileListA1.add(new File("work/CustomerA-1.drl"));
        createKieContainer(releaseIdCustomerA1, fileListA1);

        System.out.println("--- Create CustomerB version 1 KieContainer");
        List<File> fileListB1 = new ArrayList<>();
        fileListB1.add(new File("work/CommonRule-1.drl"));
        fileListB1.add(new File("work/CustomerB-1.drl"));
        createKieContainer(releaseIdCustomerB1, fileListB1);

        System.out.println("--- Use CustomerA version 1 KieContainer");
        KieContainer kcontainerA1 = ks.newKieContainer(releaseIdCustomerA1);
        KieSession ksessionA1 = kcontainerA1.newKieSession();
        try {
            ksessionA1.insert(new Person("John", 35));
            ksessionA1.fireAllRules();
        } finally {
            ksessionA1.dispose();
        }

        System.out.println("--- Use CustomerB version 1 KieContainer");
        KieContainer kcontainerB1 = ks.newKieContainer(releaseIdCustomerB1);
        KieSession ksessionB1 = kcontainerB1.newKieSession();
        try {
            ksessionB1.insert(new Person("John", 35));
            ksessionB1.fireAllRules();
        } finally {
            ksessionB1.dispose();
        }

        System.out.println("--- Create CustomerA version 2 KieContainer");
        List<File> fileListA2 = new ArrayList<>();
        fileListA2.add(new File("work/CommonRule-1.drl"));
        fileListA2.add(new File("work/CustomerA-2.drl"));
        createKieContainer(releaseIdCustomerA2, fileListA2);

        System.out.println("--- Use CustomerA version 2 KieContainer");
        KieContainer kcontainerA2 = ks.newKieContainer(releaseIdCustomerA2);
        KieSession ksessionA2 = kcontainerA2.newKieSession();
        try {
            ksessionA2.insert(new Person("John", 35));
            ksessionA2.fireAllRules();
        } finally {
            ksessionA2.dispose();
        }
    }

    private void createKieContainer(ReleaseId releaseId, List<File> fileList) {
        KieServices ks = KieServices.Factory.get();

        // KieFileSystem is responsible for gathering resources
        KieFileSystem kfs = ks.newKieFileSystem();

        for (File file : fileList) {
            // You can add your DRL files as InputStream here
            try {
                kfs.write("src/main/resources/com/sample/" + file.getName(),
                          ks.getResources().newInputStreamResource(new FileInputStream(file)));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        kfs.generateAndWritePomXML(releaseId);

        // Now resources are built and stored into an internal repository
        ks.newKieBuilder(kfs).buildAll(ExecutableModelProject.class);
    }
}
