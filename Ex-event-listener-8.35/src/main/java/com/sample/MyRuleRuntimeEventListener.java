package com.sample;

import org.drools.core.event.DefaultRuleRuntimeEventListener;
import org.kie.api.event.rule.ObjectInsertedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyRuleRuntimeEventListener extends DefaultRuleRuntimeEventListener {

    private static final Logger LOG = LoggerFactory.getLogger(MyRuleRuntimeEventListener.class);

    @Override
    public void objectInserted(final ObjectInsertedEvent event) {
        LOG.info("objectInserted : " + event.getObject());
    }
}
