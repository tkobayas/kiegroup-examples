package com.sample;

import static com.sample.Constants.ARTIFACT_ID;
import static com.sample.Constants.BASE_URL;
import static com.sample.Constants.CONTAINER_ID;
import static com.sample.Constants.GROUP_ID;
import static com.sample.Constants.VERSION;

import org.kie.server.api.model.KieContainerResource;
import org.kie.server.api.model.KieScannerResource;
import org.kie.server.api.model.KieScannerStatus;
import org.kie.server.api.model.ReleaseId;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;

public class RestartContainer {

    public static void main(String[] args) throws Exception {
        KieServicesConfiguration config = KieServicesFactory.newRestConfiguration(BASE_URL, "kieserver", "kieserver1!");
        KieServicesClient client = KieServicesFactory.newKieServicesClient(config);

        ServiceResponse<Void> response1 = client.disposeContainer(CONTAINER_ID);

        System.out.println(response1);

        Thread.sleep(3000);

        ReleaseId releaseId = new ReleaseId(GROUP_ID, ARTIFACT_ID, VERSION);
        KieContainerResource resource = new KieContainerResource(CONTAINER_ID, releaseId);
        KieScannerResource scanner = new KieScannerResource(KieScannerStatus.STARTED, 10000L);
        resource.setScanner(scanner);
        ServiceResponse<KieContainerResource> response2 = client.createContainer(CONTAINER_ID, resource);

        System.out.println(response2);
    }
}
