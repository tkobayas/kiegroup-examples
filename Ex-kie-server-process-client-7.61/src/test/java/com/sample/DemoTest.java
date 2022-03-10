package com.sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;
import org.kie.server.api.KieServerConstants;
import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.api.model.instance.TaskSummary;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.kie.server.client.ProcessServicesClient;
import org.kie.server.client.UserTaskServicesClient;

import static com.sample.Constants.BASE_URL;
import static com.sample.Constants.CONTAINER_ID;
import static com.sample.Constants.PASSWORD;
import static com.sample.Constants.USERNAME;

public class DemoTest extends TestCase {

    public void testRest() throws Exception {

        // Just for demo purpose. Should start from a clean database because this uses fixed Ids

        KieServicesConfiguration config = KieServicesFactory.newRestConfiguration(BASE_URL, USERNAME, PASSWORD);
        List<String> capabilities = new ArrayList<String>();
        capabilities.add(KieServerConstants.CAPABILITY_BPM);
        config.setCapabilities(capabilities);
        config.setMarshallingFormat(MarshallingFormat.JSON);
        KieServicesClient client = KieServicesFactory.newKieServicesClient(config);

        ProcessServicesClient processServiceClient = client.getServicesClient(ProcessServicesClient.class);

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("processVar1", "AAA1");
        params.put("processVar2", "BBB2");

        long processInstanceId = processServiceClient.startProcess(CONTAINER_ID, "project1.helloProcess", params);

        System.out.println("startProcess() : processInstanceId = " + processInstanceId);

        UserTaskServicesClient userTaskServiceClient = client.getServicesClient(UserTaskServicesClient.class);

        //        List<String> statusList = List.of("Reserved");
        //        List<TaskSummary> taskList = userTaskServiceClient.findTasksByStatusByProcessInstanceId(processInstanceId, statusList, 0, 100);
        //        System.out.println(taskList);

        List<TaskSummary> taskList = userTaskServiceClient.findTasksAssignedAsPotentialOwner(USERNAME, 0, 100);
        for (TaskSummary taskSummary : taskList) {
            System.out.println("taskSummary.getId() = " + taskSummary.getId() + ", staus = " + taskSummary.getStatus());
            long taskId = taskSummary.getId();
            if (taskSummary.getStatus().equals("Reserved")) {
                userTaskServiceClient.startTask(CONTAINER_ID, taskId, USERNAME);
            }
            userTaskServiceClient.completeTask(CONTAINER_ID, taskId, USERNAME, null);
        }

    }

}
