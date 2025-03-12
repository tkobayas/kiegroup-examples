package com.sample;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.drools.core.common.EventFactHandle;
import org.drools.core.time.impl.PseudoClockScheduler;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.kie.api.time.SessionClock;

public class DroolsTest {

    @Test
    public void testCep() {
        try {
            KieServices ks = KieServices.Factory.get();
            KieContainer kContainer = ks.getKieClasspathContainer();

            KieSession kSession = kContainer.newKieSession("ksession1");
            PseudoClockScheduler clock = (PseudoClockScheduler) kSession.<SessionClock> getSessionClock();

//            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");

//            clock.setStartupTime(sdf.parse("30-05-2016 10:00:00.000").getTime());

            Message message1 = new Message(1, "AAA");
            kSession.insert(message1);

            clock.advanceTime(20, TimeUnit.DAYS);
//            Message message2 = new Message(2, "BBB");
//            kSession.insert(message2);

            kSession.fireAllRules();
            
            clock.advanceTime(60, TimeUnit.DAYS);
            
            kSession.fireAllRules();


        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

}
