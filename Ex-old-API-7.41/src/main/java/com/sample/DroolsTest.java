package com.sample;

import java.util.HashMap;
import java.util.Map;

import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.impl.KnowledgeBaseFactory;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;

public class DroolsTest {

    public static void main(String[] args) {

        KieSession ksession = newStatefulKnowledgeSession(DroolsTest.class, "Sample.drl", new HashMap<>());

        ksession.insert(new Person("John", 35));
        ksession.insert(new Person("Paul", 33));

        ksession.fireAllRules();
        ksession.dispose();

    }

    public static final KieSession newStatefulKnowledgeSession(final Class<?> clazz, final String drl, final Map<String, String> properties) {
        final KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kbuilder.add(ResourceFactory.newClassPathResource(drl, clazz), ResourceType.DRL);

        if (kbuilder.hasErrors()) {
            throw new Error(kbuilder.getErrors().toString());
        }

        final InternalKnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
        kbase.addPackages(kbuilder.getKnowledgePackages());

        final KieSessionConfiguration config = KnowledgeBaseFactory.newKnowledgeSessionConfiguration();
        for (final Map.Entry<String, String> property : properties.entrySet()) {
            config.setProperty(property.getKey(), property.getValue());
        }

        return kbase.newKieSession(config, null);
    }
}
