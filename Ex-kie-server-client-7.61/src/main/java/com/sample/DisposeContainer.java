package com.sample;

import org.kie.server.api.model.ServiceResponse;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;

import static com.sample.Constants.BASE_URL;
import static com.sample.Constants.CONTAINER_ID;
import static com.sample.Constants.PASSWORD;
import static com.sample.Constants.USERNAME;

public class DisposeContainer {

    public static void main(String[] args) throws Exception {
        KieServicesConfiguration config = KieServicesFactory.newRestConfiguration(BASE_URL, USERNAME, PASSWORD);
        KieServicesClient client = KieServicesFactory.newKieServicesClient(config);

        ServiceResponse<Void> response1 = client.disposeContainer(CONTAINER_ID);

        System.out.println(response1);

        Thread.sleep(3000);


    }
}
