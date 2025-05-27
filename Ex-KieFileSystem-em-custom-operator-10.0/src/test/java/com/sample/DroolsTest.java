package com.sample;

import java.io.File;
import java.io.FileInputStream;

import org.drools.model.codegen.ExecutableModelProject;
import org.junit.Test;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import static org.assertj.core.api.Assertions.assertThat;

public class DroolsTest {

    @Test
    public void testRules() throws Exception {

        KieServices ks = KieServices.Factory.get();
        KieFileSystem kfs = ks.newKieFileSystem();

        kfs.write("src/main/resources/com/sample/Sample1.drl",
                  ks.getResources().newInputStreamResource(new FileInputStream(new File("work/Sample1.drl"))));

        ReleaseId releaseId = ks.newReleaseId("com.sample", "my-sample-a", "1.0.0");
        kfs.generateAndWritePomXML(releaseId);

        ks.newKieBuilder(kfs).buildAll(ExecutableModelProject.class);
//        ks.newKieBuilder(kfs).buildAll();
        KieContainer kcontainer = ks.newKieContainer(releaseId);
        KieBase kbase = kcontainer.getKieBase();
        KieSession ksession = kbase.newKieSession();
        try {
            final Person alice = new Person("Alice", 30);
            alice.addAddress(new Address("Large Street", 1));
            final Person bob = new Person("Bob", 30);
            bob.addAddress(new Address("Large Street", 1));
            bob.addAddress(new Address("Long Street", 1));

            ksession.insert(alice);
            ksession.insert(bob);

            int fired = ksession.fireAllRules();
            assertThat(fired).isEqualTo(1);
        } finally {
            ksession.dispose();
        }
    }
}
