package com.sample;

import java.io.FileInputStream;
import java.io.IOException;

import org.junit.Test;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class DeserializeTest {

    @Test
    public void testDeserialize() {
        // load up the knowledge base                                                                                                                                                                              
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        KieBase kBase = kContainer.getKieBase();
        KieSession kSession = null;

        // Deserialize                                                                                                                                                                                             
        try (FileInputStream in = new FileInputStream("./ksession.out")) {

            kSession = ks.getMarshallers().newMarshaller(kBase).unmarshall(in);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        // go !                                                                                                                                                                                                    
        Person paul = new Person("Paul", 30);
        kSession.insert(paul);
        kSession.fireAllRules();

        kSession.dispose();

    }

}
