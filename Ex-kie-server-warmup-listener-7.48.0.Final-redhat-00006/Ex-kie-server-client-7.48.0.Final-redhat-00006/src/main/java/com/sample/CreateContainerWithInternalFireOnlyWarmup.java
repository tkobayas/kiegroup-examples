package com.sample;

import org.kie.server.api.model.KieContainerResource;
import org.kie.server.api.model.KieScannerResource;
import org.kie.server.api.model.KieScannerStatus;
import org.kie.server.api.model.KieServerConfigItem;
import org.kie.server.api.model.ReleaseId;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;

import static com.sample.Constants.ARTIFACT_ID;
import static com.sample.Constants.BASE_URL;
import static com.sample.Constants.CONTAINER_ID;
import static com.sample.Constants.GROUP_ID;
import static com.sample.Constants.VERSION;

public class CreateContainerWithInternalFireOnlyWarmup {

    public static void main(String[] args) {
        KieServicesConfiguration config = KieServicesFactory.newRestConfiguration(BASE_URL, "kieserver", "kieserver1!");
        config.setTimeout(1200000);
        KieServicesClient client = KieServicesFactory.newKieServicesClient(config);

        ReleaseId releaseId = new ReleaseId(GROUP_ID, ARTIFACT_ID, VERSION);
        KieContainerResource resource = new KieContainerResource(CONTAINER_ID, releaseId);
        resource.addConfigItem(new KieServerConfigItem("org.example.kie.server.warmup.strategy", "internal-fire-only", String.class.getName()));
        KieScannerResource scanner = new KieScannerResource(KieScannerStatus.STARTED, 10000L);
        resource.setScanner(scanner);
        ServiceResponse<KieContainerResource> response = client.createContainer(CONTAINER_ID, resource);

        System.out.println(response);
    }
}
