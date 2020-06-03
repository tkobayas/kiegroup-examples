package com.sample;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.dmn.api.core.DMNContext;
import org.kie.dmn.api.core.DMNModel;
import org.kie.dmn.api.core.DMNResult;
import org.kie.dmn.api.core.DMNRuntime;
import org.kie.dmn.core.api.DMNFactory;
import org.kie.dmn.core.compiler.RuntimeTypeCheckOption;
import org.kie.dmn.core.impl.DMNRuntimeImpl;
import org.kie.dmn.core.util.KieHelper;

/**
 * This is a sample class to launch a rule.
 */
public class DMNTest {

    public static final void main(String[] args) {
        final KieServices ks = KieServices.Factory.get();
        final KieContainer kieContainer = KieHelper.getKieContainer(
                                                                    ks.newReleaseId("com.sample", "dmn-example-" + UUID.randomUUID(), "1.0"),
                                                                    ks.getResources().newClassPathResource("Traffic_Violation.dmn", DMNTest.class));

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
