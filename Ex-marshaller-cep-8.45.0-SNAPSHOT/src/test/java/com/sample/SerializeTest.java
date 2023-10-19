package com.sample;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.api.runtime.conf.ClockTypeOption;
import org.kie.api.time.SessionPseudoClock;

import static org.junit.Assert.assertEquals;

public class SerializeTest {

    @Test
    public void testSerialize() {
        // load up the knowledge base                                                                                                                                                                              
        KieServices ks = KieServices.get();
        KieContainer kContainer = ks.getKieClasspathContainer();

        KieSessionConfiguration config = KieServices.Factory.get().newKieSessionConfiguration();
        config.setOption(ClockTypeOption.PSEUDO);
        KieSession kieSession = kContainer.getKieBase().newKieSession(config,null);

        SessionPseudoClock clock = kieSession.getSessionClock();

        EventNotificationChannel notificationChannel = new EventNotificationChannel();
        kieSession.registerChannel("notification-channel", notificationChannel);

        String eventId = "beb0af1a-97f2-42bb-9af9-94990e38f221";


        Event eventCreated = new Event();
        eventCreated.setEventId(eventId);
        eventCreated.setStatus("created");

        kieSession.insert(eventCreated);
        System.out.println("Facts inserted - " + kieSession.getFactHandles());


        long ruleFireCount = kieSession.fireAllRules();
        System.out.println(ruleFireCount);
        assertEquals(0, ruleFireCount);
        System.out.println("advancing time");

        clock.advanceTime(61, TimeUnit.SECONDS);
        System.out.println("Session clock -> " + kieSession.getSessionClock());

        System.out.println("Session clock -> " + new Date(kieSession.getSessionClock().getCurrentTime()));

        // Serialize                                                                                                                                                                                              
        try (FileOutputStream out = new FileOutputStream("./ksession.out")) {

            ks.getMarshallers().newMarshaller(kieSession.getKieBase()).marshall(out, kieSession);

        } catch (IOException e) {
            e.printStackTrace();
        }

        kieSession.dispose();

        System.out.println("=== finished");
    }

}
