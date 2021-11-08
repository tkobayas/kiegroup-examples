package com.sample;

import static com.sample.Constants.BASE_URL;

import org.kie.server.api.model.KieServerInfo;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;

public class KieRestClientBasic {

    public static void main(String[] args) {
        KieServicesConfiguration config = KieServicesFactory.newRestConfiguration(BASE_URL, "kieserver", "kieserver1!");

        KieServicesClient client = KieServicesFactory.newKieServicesClient(config);
        ServiceResponse<KieServerInfo> response = client.getServerInfo();
        System.out.println(response.getResult());
    }
}
