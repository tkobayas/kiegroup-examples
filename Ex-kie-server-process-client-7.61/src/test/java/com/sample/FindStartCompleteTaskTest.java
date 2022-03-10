package com.sample;

import java.util.List;

import junit.framework.TestCase;
import org.kie.server.client.UserTaskServicesClient;

import static com.sample.Constants.CONTAINER_ID;
import static com.sample.Constants.USERNAME;

public class FindStartCompleteTaskTest extends TestCase {

    public void testRest() throws Exception {

        UserTaskServicesClient taskClient = KieServerRestUtils.getUserTaskServicesClient();

        List<org.kie.server.api.model.instance.TaskSummary> taskList;
        taskList = taskClient.findTasksAssignedAsPotentialOwner(USERNAME, 0, 100);
        for (org.kie.server.api.model.instance.TaskSummary taskSummary : taskList) {
            System.out.println("taskSummary.getId() = " + taskSummary.getId() + ", staus = " + taskSummary.getStatus());
            long taskId = taskSummary.getId();
            if (taskSummary.getStatus().equals("Reserved")) {
                taskClient.startTask(CONTAINER_ID, taskId, USERNAME);
            }
            taskClient.completeTask(CONTAINER_ID, taskId, USERNAME, null);
        }

    }
}
