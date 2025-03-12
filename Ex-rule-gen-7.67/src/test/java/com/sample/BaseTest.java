package com.sample;

import java.util.List;
import java.util.UUID;

import org.drools.compiler.kie.builder.impl.DrlProject;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.builder.ReleaseId;
import org.kie.api.builder.model.KieModuleModel;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import static org.junit.Assert.fail;

public class BaseTest {

    protected KieSession getKieSession(String drl) {
        KieServices ks = KieServices.get();
        ReleaseId releaseId = ks.newReleaseId("org.kie", "kjar-test-" + UUID.randomUUID(), "1.0");

        KieModuleModel model = KieServices.get().newKieModuleModel();
        ks.getRepository().removeKieModule(releaseId);

        KieFileSystem kfs = ks.newKieFileSystem();
        kfs.writeKModuleXML(model.toXML());
        kfs.writePomXML(getPom(releaseId));
        kfs.write("src/main/resources/com/sample/Sample1.drl", drl);

        KieBuilder kieBuilder = ks.newKieBuilder(kfs).buildAll(DrlProject.class);
        //        KieBuilder kieBuilder = ks.newKieBuilder(kfs).buildAll(ExecutableModelProject.class);

        List<Message> messages = kieBuilder.getResults().getMessages();
        if (!messages.isEmpty()) {
            fail(messages.toString());
        }

        KieContainer kieContainer = ks.newKieContainer(releaseId);
        return kieContainer.newKieSession();
    }

    public static String getPom(ReleaseId releaseId) {
        String pom =
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                     "<project xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                     "         xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd\">\n" +
                     "  <modelVersion>4.0.0</modelVersion>\n" +
                     "\n" +
                     "  <groupId>" + releaseId.getGroupId() + "</groupId>\n" +
                     "  <artifactId>" + releaseId.getArtifactId() + "</artifactId>\n" +
                     "  <version>" + releaseId.getVersion() + "</version>\n" +
                     "</project>";
        return pom;
    }
}
