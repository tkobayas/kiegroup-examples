package com.sample;

import java.util.Collection;

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

    public static void main(String[] args) {

        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        Resource resource1 = ResourceFactory.newClassPathResource("Sample.drl", DroolsTest.class);
        kbuilder.add(resource1, ResourceType.DRL);
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
