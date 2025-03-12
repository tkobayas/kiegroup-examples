package com.sample;

import org.drools.core.event.DefaultAgendaEventListener;
import org.drools.core.rule.consequence.Activation;
import org.kie.api.event.rule.BeforeMatchFiredEvent;

public class MyAgendaEventListener extends DefaultAgendaEventListener {

    public void beforeMatchFired(BeforeMatchFiredEvent event) {
        String agendaGroupName = ((Activation)event.getMatch()).getAgendaGroup().getName();
        System.out.println("agendaGroupName : " + agendaGroupName);
        String ruleName = event.getMatch().getRule().getName();
        System.out.println("ruleName : " + ruleName);
    }
}
