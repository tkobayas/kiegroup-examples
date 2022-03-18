package com.sample;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;

import org.drools.compiler.compiler.DrlParser;
import org.drools.compiler.compiler.DroolsParserException;
import org.drools.compiler.lang.descr.PackageDescr;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.internal.KnowledgeBase;
import org.kie.internal.KnowledgeBaseFactory;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.definition.KnowledgePackage;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.runtime.StatefulKnowledgeSession;

public class DroolsTest {

    public static void main(String[] args) throws DroolsParserException {

        InputStream is = DroolsTest.class.getClassLoader().getResourceAsStream("com/sample/Sample.drl");

        DrlParser drlParser = new DrlParser();
        PackageDescr packageDescr = drlParser.parse(new InputStreamReader(is));
        Resource packageDescrResource = ResourceFactory.newDescrResource(packageDescr);
        
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kbuilder.add(packageDescrResource, ResourceType.DESCR);
        Collection<KnowledgePackage> knowledgePackages = kbuilder.getKnowledgePackages();
        KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
        kbase.addKnowledgePackages(knowledgePackages);
        StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();

        ksession.insert(new Person("John", 35));
        ksession.insert(new Person("Paul", 33));

        ksession.fireAllRules();
        ksession.dispose();
    }
}
