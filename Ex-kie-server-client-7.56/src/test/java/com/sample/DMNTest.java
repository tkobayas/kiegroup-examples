package com.sample;

import static com.sample.Constants.BASE_URL;
import static com.sample.Constants.CONTAINER_ID;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.command.BatchExecutionCommand;
import org.kie.api.command.Command;
import org.kie.api.command.KieCommands;
import org.kie.api.runtime.ExecutionResults;
import org.kie.dmn.api.core.DMNContext;
import org.kie.dmn.api.core.DMNDecisionResult;
import org.kie.dmn.api.core.DMNResult;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.client.DMNServicesClient;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.kie.server.client.RuleServicesClient;

public class DMNTest extends TestCase {

    private static final String USERNAME = "kieserver";
    private static final String PASSWORD = "kieserver1!";

    private static final String KSESSION_NAME = "myStatelessKsession";

    private static final String GLOBAL_IDENTIFIER = "results";

    //    private static final MarshallingFormat FORMAT = MarshallingFormat.JSON;
    //    private static final MarshallingFormat FORMAT = MarshallingFormat.JAXB;

    @Test
    public void testProcess() {

        KieServicesConfiguration config = KieServicesFactory.newRestConfiguration(BASE_URL, USERNAME, PASSWORD);
        //        config.setMarshallingFormat(FORMAT);
        //        HashSet<Class<?>> classes = new HashSet<Class<?>>();
        //        classes.add(Person.class);
        //
        //        config.addExtraClasses(classes);
        KieServicesClient kieServicesClient = KieServicesFactory.newKieServicesClient(config);

        DMNServicesClient dmnClient = kieServicesClient.getServicesClient(DMNServicesClient.class);

        DMNContext dmnContext = dmnClient.newContext();
        
        Map<String, Object> driverMap = new HashMap<>();
        driverMap.put("Points", 2);
        dmnContext.set("Driver", driverMap);
        
        Map<String, Object> violationMap = new HashMap<>();
        violationMap.put("Type", "speed");
        violationMap.put("Actual Speed", 120);
        violationMap.put("Speed Limit", 100);
        dmnContext.set("Violation", violationMap);
        
        ServiceResponse<DMNResult> serverResp =
                dmnClient.evaluateAll(CONTAINER_ID,
                                      "https://github.com/kiegroup/drools/kie-dmn/_A4BCA8B8-CF08-433F-93B2-A2598F19ECFF",
                                      "Traffic Violation",
                                      dmnContext);

        DMNResult dmnResult = serverResp.getResult();
        for (DMNDecisionResult dr : dmnResult.getDecisionResults()) {
            System.out.println(
                               "Decision: '" + dr.getDecisionName() + "', " +
                               "Result: " + dr.getResult());
        }

    }

}
