package com.sample;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.drools.compiler.compiler.io.memory.MemoryFileSystem;
import org.drools.compiler.kie.builder.impl.MemoryKieModule;
import org.drools.modelcompiler.CanonicalKieModule;
import org.drools.modelcompiler.ExecutableModelProject;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.ReleaseId;

public class CreateKjarBinaryTest {

    @Test
    public void testCreateKjar() throws Exception {

        // KieServices is the entry point of APIs
        KieServices ks = KieServices.Factory.get();

        // KieFileSystem is responsible for gathering resources
        KieFileSystem kfs = ks.newKieFileSystem();

        // You can add your DRL files as InputStream here
        kfs.write("src/main/resources/com/sample/Sample.drl",
                  ks.getResources().newInputStreamResource(new FileInputStream(new File("work/com/sample/Sample.drl"))));

        // You need to specify a unique ReleaseId per KieContainer (= the unit which you store a set of DRL files)
        // ReleaseId consists of "GroupId" + "ArtifactId" + "Version". The same idea of Maven artifact.
        ReleaseId releaseId = ks.newReleaseId("com.sample", "basic-kjar-em-55-kfs", "1.0.0");
        kfs.generateAndWritePomXML(releaseId);

        // Now resources are built and stored into an internal repository
        KieBuilder kieBuilder = ks.newKieBuilder(kfs);

        System.out.println("Start building...");
        kieBuilder.buildAll(ExecutableModelProject.class);

        CanonicalKieModule kieModule = (CanonicalKieModule) kieBuilder.getKieModule();

        MemoryKieModule memoryKieModule = (MemoryKieModule) kieModule.getInternalKieModule();
        MemoryFileSystem mfs = memoryKieModule.getMemoryFileSystem();
        byte[] kjarBinary = mfs.writeAsBytes();
        Path path = Paths.get("./output/basic-kjar-em-55-kfs-1.0.0.jar"); // already zip structure

        try {
            Files.write(path, kjarBinary, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("---- done");
    }
}
