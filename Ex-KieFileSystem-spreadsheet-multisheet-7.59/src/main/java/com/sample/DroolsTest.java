package com.sample;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.StringReader;

import org.drools.modelcompiler.ExecutableModelProject;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class DroolsTest {

    public static void main(String[] args) throws Exception {

        KieContainer kcontainer1 = createKieContainer("sheet1");
        KieContainer kcontainer2 = createKieContainer("sheet2");

        runRules(kcontainer1);
        runRules(kcontainer2);
    }

    private static void runRules(KieContainer kcontainer) {
        // KieContainer can create a KieBase
        KieBase kbase = kcontainer.getKieBase();

        // KieBase can create a KieSession
        KieSession ksession = kbase.newKieSession();

        Message message = new Message();
        message.setMessage("Hello World");
        message.setStatus(Message.HELLO);
        ksession.insert(message);
        ksession.fireAllRules();
        ksession.dispose();
    }

    private static KieContainer createKieContainer(String sheetName) throws FileNotFoundException {
        // KieServices is the entry point of APIs
        KieServices ks = KieServices.Factory.get();

        // KieFileSystem is responsible for gathering resources
        KieFileSystem kfs = ks.newKieFileSystem();

        // You can add your DRL/XLS files as InputStream here
        kfs.write("src/main/resources/com/sample/Sample.xls",
                  ks.getResources().newInputStreamResource(new FileInputStream(new File("work/com/sample/Sample.xls"))));
        kfs.write("src/main/resources/com/sample/Sample.xls.properties",
                  ks.getResources().newReaderResource(new StringReader("sheets=" + sheetName))); // programmatically create sheet property

        // You need to specify a unique ReleaseId per KieContainer (= the unit which you store a set of DRL files)
        // ReleaseId consists of "GroupId" + "ArtifactId" + "Version". The same idea of Maven artifact.
        ReleaseId releaseId = ks.newReleaseId("com.sample", "my-sample-" + sheetName, "1.0.0");
        kfs.generateAndWritePomXML(releaseId);

        // Now resources are built and stored into an internal repository
        ks.newKieBuilder(kfs).buildAll(ExecutableModelProject.class);

        // You can get a KieContainer with the ReleaseId
        KieContainer kcontainer = ks.newKieContainer(releaseId);
        return kcontainer;
    }
}
