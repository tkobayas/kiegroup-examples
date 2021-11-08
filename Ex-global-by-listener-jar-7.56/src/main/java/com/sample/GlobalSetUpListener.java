package com.sample;

import java.util.ArrayList;
import java.util.List;

import org.kie.api.event.rule.ObjectDeletedEvent;
import org.kie.api.event.rule.ObjectInsertedEvent;
import org.kie.api.event.rule.ObjectUpdatedEvent;
import org.kie.api.event.rule.RuleRuntimeEventListener;
import org.kie.api.runtime.KieSession;

public class GlobalSetUpListener implements RuleRuntimeEventListener {

    @Override
    public void objectDeleted(ObjectDeletedEvent arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void objectInserted(ObjectInsertedEvent event) {
        System.out.println(event);
        Object obj = event.getObject();
        if (obj instanceof String && "reset".equals(obj)) {
            System.out.println("RESET!!");
            KieSession ksession = (KieSession)event.getKieRuntime();
            List<String> results = new ArrayList<>();
            ksession.setGlobal("results", results);
        }
    }

    @Override
    public void objectUpdated(ObjectUpdatedEvent arg0) {
        // TODO Auto-generated method stub

    }

}
