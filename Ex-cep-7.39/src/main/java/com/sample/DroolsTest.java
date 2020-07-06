package com.sample;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.drools.core.time.impl.PseudoClockScheduler;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.time.SessionClock;
import org.kie.api.time.SessionPseudoClock;

/**
 * This is a sample class to launch a rule.
 */
public class DroolsTest {

    public static final void main(String[] args) {
        try {
            // load up the knowledge base
	        KieServices ks = KieServices.Factory.get();
    	    KieContainer kContainer = ks.getKieClasspathContainer();
            
        	KieSession kSession = kContainer.newKieSession("ksession1");
        	PseudoClockScheduler clock = (PseudoClockScheduler) kSession.<SessionClock>getSessionClock();

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
            
            clock.setStartupTime(sdf.parse("30-05-2016 10:00:00.000").getTime());
            
            Message message1 = new Message(1, "AAA");
            kSession.insert(message1);

            clock.advanceTime(200, TimeUnit.DAYS);
            Message message2 = new Message(2, "BBB");
            kSession.insert(message2);
            
            clock.advanceTime(30, TimeUnit.DAYS);
            Message message3 = new Message(3, "CCC");
            kSession.insert(message3);
            
            clock.advanceTime(200, TimeUnit.DAYS);
            Message message4 = new Message(4, "DDD");
            kSession.insert(message4);
            
            clock.advanceTime(80, TimeUnit.DAYS);
            Message message5 = new Message(5, "EEE");
            kSession.insert(message5);

            kSession.fireAllRules();
            
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }


}
