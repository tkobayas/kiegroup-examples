/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.server.ext.prometheus;

import org.jbpm.executor.AsynchronousJobListener;
import org.jbpm.services.api.DeploymentEventListener;
import org.kie.api.event.rule.AgendaEventListener;
import org.kie.dmn.api.core.event.DMNRuntimeEventListener;
import org.kie.server.services.api.KieContainerInstance;
import org.kie.server.services.prometheus.PrometheusKieServerExtension;
import org.kie.server.services.prometheus.PrometheusMetricsDroolsListener;
import org.kie.server.services.prometheus.PrometheusMetricsProvider;
import org.optaplanner.core.impl.phase.event.PhaseLifecycleListener;

public class MyPrometheusMetricsProvider implements PrometheusMetricsProvider {

    public DMNRuntimeEventListener createDMNRuntimeEventListener(KieContainerInstance kContainer) {
        return null;
    }

    public AgendaEventListener createAgendaEventListener(String kieSessionId, KieContainerInstance kContainer) {
        System.out.println("**** createAgendaEventListener");
        return new PrometheusMetricsDroolsListener(PrometheusKieServerExtension.getMetrics(), kieSessionId, kContainer);
    }

    public PhaseLifecycleListener createPhaseLifecycleListener(String solverId) {
        System.out.println("**** createPhaseLifecycleListener");
        return null;
    }

    public AsynchronousJobListener createAsynchronousJobListener() {
        System.out.println("**** createAsynchronousJobListener");
        return null;
    }

    public DeploymentEventListener createDeploymentEventListener() {
        System.out.println("**** createDeploymentEventListener");
        return null;
    }

}
