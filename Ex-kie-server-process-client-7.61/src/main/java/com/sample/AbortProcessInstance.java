package com.sample;

import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.kie.server.client.ProcessServicesClient;

import static com.sample.Constants.BASE_URL;
import static com.sample.Constants.CONTAINER_ID;
import static com.sample.Constants.PASSWORD;
import static com.sample.Constants.USERNAME;

public class AbortProcessInstance {


    
    public static void main(String[] args) {

        KieServicesConfiguration config = KieServicesFactory.newRestConfiguration(BASE_URL, USERNAME, PASSWORD);
        KieServicesClient client = KieServicesFactory.newKieServicesClient(config);

        ProcessServicesClient proessClient = client.getServicesClient(ProcessServicesClient.class);

        proessClient.abortProcessInstance(CONTAINER_ID, 1L);

    }
}
