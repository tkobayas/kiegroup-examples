package com.sample;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import org.kie.server.client.ProcessServicesClient;

import static com.sample.Constants.CONTAINER_ID;
import static com.sample.Constants.PASSWORD;
import static com.sample.Constants.USERNAME;

public class StartProcessTest extends TestCase {

    public void testRest() throws Exception {

        Map<String, Object> params = new HashMap<String, Object>();
        ProcessServicesClient processClient = KieServerRestUtils.getProcessServicesClient(USERNAME, PASSWORD);

        long processInstanceId = processClient.startProcess(CONTAINER_ID, "project1.ruleProcess", params);

        System.out.println("startProcess() : processInstanceId = " + processInstanceId);



    }
}