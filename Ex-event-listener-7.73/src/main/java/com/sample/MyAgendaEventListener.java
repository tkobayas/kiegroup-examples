package com.sample;

import org.drools.core.event.DefaultAgendaEventListener;
import org.drools.core.spi.Activation;
import org.kie.api.event.rule.BeforeMatchFiredEvent;
import org.kie.api.runtime.rule.Match;


public class MyAgendaEventListener extends DefaultAgendaEventListener {

    public void beforeMatchFired(BeforeMatchFiredEvent event) {
        Match match = event.getMatch();
        System.out.println(match);
        String agendaGroupName = ((Activation)match).getAgendaGroup().getName();
        System.out.println(agendaGroupName);
    }
}
