package com.sample;

import junit.framework.TestCase;
import org.kie.server.client.UserTaskServicesClient;

import static com.sample.Constants.CONTAINER_ID;
import static com.sample.Constants.PASSWORD;
import static com.sample.Constants.USERNAME;

public class StartAndCompleteTaskTest extends TestCase {

    public void testRest() throws Exception {

        UserTaskServicesClient taskClient = KieServerRestUtils.getUserTaskServicesClient(USERNAME, PASSWORD);

        taskClient.startTask(CONTAINER_ID, 3L, USERNAME);
        taskClient.completeTask(CONTAINER_ID, 3L, USERNAME, null);

    }
}
