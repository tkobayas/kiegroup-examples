package com.sample;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.drools.decisiontable.ExternalSpreadsheetCompiler;
import org.junit.Test;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.ReleaseId;
import org.kie.api.definition.KiePackage;
import org.kie.api.io.Resource;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class DroolsTest {

    @Test
    public void testTemplate() {

        // first, generate a drl using template and values
        final ExternalSpreadsheetCompiler converter = new ExternalSpreadsheetCompiler();

        final KieServices ks = KieServices.Factory.get();

        final Resource valueTable = ks.getResources().newClassPathResource("sample_cheese.xls", DroolsTest.class);
        final Resource template = ks.getResources().newClassPathResource("sample_cheese.drt", DroolsTest.class);

        String drl = null;
        try {
            // the data we are interested in starts at row 2, column 2 (i.e. B2)
            drl = converter.compile(valueTable.getInputStream(), template.getInputStream(), 2, 2);
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not read spreadsheet or rules stream.", e);
        }

        // build the generated drl
        final Resource drlResource = ks.getResources().newReaderResource(new StringReader(drl));
        drlResource.setTargetPath("src/main/resources/rule.drl");

        KieFileSystem kfs = ks.newKieFileSystem();
        kfs.write("src/main/resources/com/sample/rule.drl", drlResource);
        ReleaseId releaseId = ks.newReleaseId("com.sample", "my-sample-a", "1.0.0");
        kfs.generateAndWritePomXML(releaseId);
        ks.newKieBuilder(kfs).buildAll();
        KieContainer kcontainer = ks.newKieContainer(releaseId);

        KieBase kbase = kcontainer.getKieBase();

        final Collection<KiePackage> pkgs = kbase.getKiePackages();

        System.out.println("pkgs = " + pkgs);

        final KieSession ksession = kbase.newKieSession();

        ksession.insert(new Cheese("stilton"));
        ksession.insert(new Person("michael", 42));
        final List<String> list = new ArrayList<String>();
        ksession.setGlobal("list", list);

        ksession.fireAllRules();

        System.out.println("list = " + list.toString());

        ksession.dispose();

    }
}
