package com.sample;

import org.drools.core.common.InternalAgenda;
import org.kie.api.runtime.KieContext;

public class Utils {

    public static void activateRuleFlowGroup(String ruleFlowGroup, KieContext kcontext) {
        ((InternalAgenda) kcontext.getKnowledgeRuntime().getAgenda()).activateRuleFlowGroup(ruleFlowGroup);
    }
}
