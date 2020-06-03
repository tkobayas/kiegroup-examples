package com.sample;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.dmn.api.core.DMNContext;
import org.kie.dmn.api.core.DMNModel;
import org.kie.dmn.api.core.DMNResult;
import org.kie.dmn.api.core.DMNRuntime;
import org.kie.dmn.core.api.DMNFactory;
import org.kie.dmn.core.compiler.RuntimeTypeCheckOption;
import org.kie.dmn.core.impl.DMNRuntimeImpl;
import org.kie.dmn.core.util.KieHelper;

public class DMNTest {

    @Test
    public void testDMN() {
        final KieServices ks = KieServices.Factory.get();
        ReleaseId releaseId = ks.newReleaseId("com.sample", "dmn-kjar", "1.0.0-SNAPSHOT");
        KieContainer kieContainer = ks.newKieContainer(releaseId);
        DMNRuntime dmnRuntime = kieContainer.newKieSession().getKieRuntime(DMNRuntime.class);
        ((DMNRuntimeImpl) dmnRuntime).setOption(new RuntimeTypeCheckOption(true));

        final DMNModel dmnModel = dmnRuntime.getModel("https://github.com/kiegroup/drools/kie-dmn/_A4BCA8B8-CF08-433F-93B2-A2598F19ECFF", "Traffic Violation");

        final DMNContext context = DMNFactory.newContext();

        Map<String, Object> driverMap = new HashMap<>();
        driverMap.put("Name", "John");
        driverMap.put("Age", 34);
        driverMap.put("State", "Texas");
        driverMap.put("City", "Austin");
        driverMap.put("Points", 20);
        context.set("Driver", driverMap);

        Map<String, Object> violationMap = new HashMap<>();
        violationMap.put("Code", "AAA");
        violationMap.put("Date", LocalDate.now());
        violationMap.put("Type", "speed");
        violationMap.put("Speed Limit", 100);
        violationMap.put("Actual Speed", 120);
        context.set("Violation", violationMap);

        final DMNResult dmnResult = dmnRuntime.evaluateAll(dmnModel, context);

        System.out.println(dmnResult);
    }

}
