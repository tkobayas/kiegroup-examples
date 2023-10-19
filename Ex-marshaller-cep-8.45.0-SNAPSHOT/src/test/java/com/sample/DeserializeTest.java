package com.sample;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

import org.junit.Test;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.api.runtime.conf.ClockTypeOption;

import static org.junit.Assert.assertEquals;

public class DeserializeTest {

    @Test
    public void testDeserialize() throws InterruptedException {
        // load up the knowledge base                                                                                                                                                                              
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        KieBase kBase = kContainer.getKieBase();
        KieSession deserializeSession = null;
        String eventId = "beb0af1a-97f2-42bb-9af9-94990e38f221";
        // Deserialize                                                                                                                                                                                            
        try (FileInputStream in = new FileInputStream("./ksession.out")) {

            KieSessionConfiguration config = KieServices.Factory.get().newKieSessionConfiguration();
            config.setOption(ClockTypeOption.PSEUDO);
            deserializeSession = ks.getMarshallers().newMarshaller(kBase).unmarshall(in, config, null);
            System.out.println("Session clock -> " + deserializeSession.getSessionClock());

            System.out.println("Session clock -> " + new Date(deserializeSession.getSessionClock().getCurrentTime()));

            EventNotificationChannel notificationChannel = new EventNotificationChannel();
            deserializeSession.registerChannel("notification-channel", notificationChannel);

            System.out.println("Facts available - " + deserializeSession.getFactHandles());


            System.out.println("firing rules again");

            int ruleFireCount = deserializeSession.fireAllRules();


            System.out.println(ruleFireCount);
            assertEquals(2, ruleFireCount);

            Event event = notificationChannel.getClosedEvent(eventId);
            assertEquals("closed", event.getStatus());
            assertEquals("auto-closed", event.getReason());
            deserializeSession.dispose();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}
