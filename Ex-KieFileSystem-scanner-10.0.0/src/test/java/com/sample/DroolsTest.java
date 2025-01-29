package com.sample;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

import org.drools.compiler.kie.builder.impl.InternalKieModule;
import org.drools.model.codegen.ExecutableModelProject;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.KieScanner;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.scanner.KieMavenRepository;

import static org.kie.scanner.KieMavenRepository.getKieMavenRepository;

public class DroolsTest {

    private static AtomicBoolean stop = new AtomicBoolean(false);

    @Test
    public void testRules() throws Exception {

        // Clean up
        cleanup();

        KieServices ks = KieServices.Factory.get();

        // Build 1.0.0
        ReleaseId releaseId100 = ks.newReleaseId("com.sample", "my-sample-a", "1.0.0");
        buildAndInstallRules(releaseId100, new File("work/Sample1.drl"));

        // run ksession in a separate thread
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> runKieSession());

        // wait a while
        Thread.sleep(3000L);

        // Build 1.1.0
        ReleaseId releaseId110 = ks.newReleaseId("com.sample", "my-sample-a", "1.1.0");
        buildAndInstallRules(releaseId110, new File("work/Sample1.drl"), new File("work/Sample2.drl"));

        // wait a while
        Thread.sleep(3000L);

        // stop ksession
        stop.set(true);
        future.join();
    }

    // Deleting the directory fails if this is the first time running this test. Don't worry.
    private static void cleanup() {
        Path directory = Path.of(System.getProperty("user.home"), ".m2", "repository", "com", "sample", "my-sample-a");
        try {
            Files.walk(directory)
                    .sorted((a, b) -> b.compareTo(a)) // Reverse order to delete files first
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                        } catch (IOException e) {
                            System.out.println("Failed to delete the directory: " + e.getMessage());
                            //e.printStackTrace();
                        }
                    });

            System.out.println("Directory deleted successfully!");
        } catch (IOException e) {
            System.out.println("Failed to delete the directory: " + e.getMessage());
            //e.printStackTrace();
        }
    }

    private static void runKieSession() {

        KieServices ks = KieServices.Factory.get();

        // Use ranged version to keep updating the rules from 1.0 (inclusive) to 2.0 (not inclusive)
        ReleaseId rangedReleaseId = ks.newReleaseId("com.sample", "my-sample-a", "[1.0,2.0)");
        KieContainer kContainer = ks.newKieContainer(rangedReleaseId);
        KieScanner kScanner = ks.newKieScanner(kContainer);

        kScanner.start(1000L);

        for (int i = 0; i < 100; i++) {
            KieSession kSession = kContainer.newKieSession();
            try {
                Person john = new Person("john", 20);
                kSession.insert(john);
                kSession.fireAllRules();
                if (stop.get()) {
                    break;
                }
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                // ignore
            } finally {
                kSession.dispose();
            }
        }
    }

    // Programatically build and install rules
    // You can replace this part with a separate kjar Maven project with kie-maven-plugin
    private void buildAndInstallRules(ReleaseId releaseId, File... files) {
        try {
            System.out.println("--- Building rules for " + releaseId);
            KieServices ks = KieServices.Factory.get();
            KieFileSystem kfs = ks.newKieFileSystem();
            for (File file : files) {
                kfs.write("src/main/resources/com/sample/" + file.getName(),
                          ks.getResources().newInputStreamResource(new FileInputStream(file)));
            }
            kfs.generateAndWritePomXML(releaseId);
            kfs.writeKModuleXML(ks.newKieModuleModel().toXML());
            KieBuilder kieBuilder = ks.newKieBuilder(kfs);
            kieBuilder.buildAll(ExecutableModelProject.class);

            // This part is required is not required if you just run the rules
            // This part is required to physically install the jar into the local Maven repository, so that make KieScanner work
            KieMavenRepository repository = getKieMavenRepository();
            KieModule kieModule = kieBuilder.getKieModule();
            Path filePath = Path.of("work/pom.xml");
            Files.writeString(filePath, new String(kfs.read("pom.xml")));
            repository.installArtifact(releaseId, (InternalKieModule) kieModule, filePath.toFile());
            System.out.println("--- Rules built ---");
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
