package com.sample;

import org.drools.core.event.DefaultAgendaEventListener;
import org.kie.api.event.rule.BeforeMatchFiredEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyAgendaEventListener extends DefaultAgendaEventListener {

    private static final Logger LOG = LoggerFactory.getLogger(MyAgendaEventListener.class);

    @Override
    public void beforeMatchFired(BeforeMatchFiredEvent event) {
        LOG.info("beforeMatchFired : matched objects = " + event.getMatch().getObjects());
        LOG.info("                   matched ruleName = " + event.getMatch().getRule().getName());
    }
}
