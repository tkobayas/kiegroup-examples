package com.sample;

import org.junit.Test;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.definition.type.FactType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class DroolsTest {

    @Test
    public void testRules() throws InstantiationException, IllegalAccessException {

//        System.setProperty("drools.dump.dir", "/home/tkobayas/tmp");

        KieServices ks = KieServices.Factory.get();
        KieContainer kcontainer = ks.getKieClasspathContainer();
        KieBase kbase = kcontainer.getKieBase();
        KieSession ksession = kbase.newKieSession();

//        ReteDumper.dumpRete(ksession);

        try {
            FactType objectReferenceType = kbase.getFactType("com.example.reproducer", "ObjectReference");
            Object objectReferenceInstance1 = objectReferenceType.newInstance();
            objectReferenceType.set(objectReferenceInstance1, "id", 1);
            objectReferenceType.set(objectReferenceInstance1, "description", "Input Object 1");
            objectReferenceType.set(objectReferenceInstance1, "type1", "");
            objectReferenceType.set(objectReferenceInstance1, "type2", "");

            Object objectReferenceInstance2 = objectReferenceType.newInstance();
            objectReferenceType.set(objectReferenceInstance2, "id", 1);
            objectReferenceType.set(objectReferenceInstance2, "description", "Input Object 1");
            objectReferenceType.set(objectReferenceInstance2, "type1", "type1");
            objectReferenceType.set(objectReferenceInstance2, "type2", "type2");

            FactType alphaInputObjectType = kbase.getFactType("com.example.reproducer", "AlphaInputObject");
            Object alphaInputObjectInstance = alphaInputObjectType.newInstance();
            alphaInputObjectType.set(alphaInputObjectInstance, "ref", objectReferenceInstance1);
            alphaInputObjectType.set(alphaInputObjectInstance, "value", 2);

            FactType betaInputObjectType = kbase.getFactType("com.example.reproducer", "BetaInputObject");
            Object betaInputObjectInstance = betaInputObjectType.newInstance();
            betaInputObjectType.set(betaInputObjectInstance, "ref", objectReferenceInstance2);
            betaInputObjectType.set(betaInputObjectInstance, "type3", "type3");
            betaInputObjectType.set(betaInputObjectInstance, "answered", true);

            ksession.insert(alphaInputObjectInstance);
            ksession.insert(betaInputObjectInstance);

            ksession.fireAllRules();
        } finally {
            ksession.dispose();
        }
    }
}
