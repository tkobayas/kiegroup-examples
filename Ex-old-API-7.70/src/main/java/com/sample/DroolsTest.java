package com.sample;

import java.util.Collection;

import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.impl.KnowledgeBaseFactory;
import org.kie.api.KieServices;
import org.kie.api.definition.KiePackage;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;

public class DroolsTest {

    public static void main(String[] args) {

        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        Resource resource1 = KieServices.Factory.get().getResources().newClassPathResource("Sample.drl", DroolsTest.class);
        kbuilder.add(resource1, ResourceType.DRL);
        Collection<KiePackage> knowledgePackages = kbuilder.getKnowledgePackages();
        InternalKnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
        kbase.addPackages(knowledgePackages);
        KieSession ksession = kbase.newKieSession();

        ksession.insert(new Person("John", 35));
        ksession.insert(new Person("Paul", 33));

        ksession.fireAllRules();
        ksession.dispose();

    }
}
