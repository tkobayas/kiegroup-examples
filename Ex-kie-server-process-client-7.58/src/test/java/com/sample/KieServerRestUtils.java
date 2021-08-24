package com.sample;

import static com.sample.Constants.BASE_URL;

import java.util.ArrayList;
import java.util.List;

import org.kie.server.api.KieServerConstants;
import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.kie.server.client.ProcessServicesClient;
import org.kie.server.client.QueryServicesClient;
import org.kie.server.client.RuleServicesClient;
import org.kie.server.client.UserTaskServicesClient;

public class KieServerRestUtils {

    private static final String DEFAULT_USERNAME = "kieserver";
    private static final String DEFAULT_PASSWORD = "kieserver1!";


    public static KieServicesClient getKieServicesClient(String username, String password) {

        KieServicesConfiguration config = KieServicesFactory.newRestConfiguration(BASE_URL, username, password);
        List<String> capabilities = new ArrayList<String>();
        capabilities.add(KieServerConstants.CAPABILITY_BPM);
        config.setCapabilities(capabilities);
        KieServicesClient client = KieServicesFactory.newKieServicesClient(config);

        return client;
    }

    public static UserTaskServicesClient getUserTaskServicesClient() {
        return getUserTaskServicesClient(DEFAULT_USERNAME, DEFAULT_PASSWORD);
    }

    public static UserTaskServicesClient getUserTaskServicesClient(String username, String password) {

        KieServicesConfiguration config = KieServicesFactory.newRestConfiguration(BASE_URL, username, password);
        List<String> capabilities = new ArrayList<String>();
        capabilities.add(KieServerConstants.CAPABILITY_BPM);
        config.setCapabilities(capabilities);
        config.setTimeout(600000);
        KieServicesClient client = KieServicesFactory.newKieServicesClient(config);

        UserTaskServicesClient userTaskServiceClient = client.getServicesClient(UserTaskServicesClient.class);

        return userTaskServiceClient;
    }
    
    public static QueryServicesClient getQueryServicesClient() {
        return getQueryServicesClient(DEFAULT_USERNAME, DEFAULT_PASSWORD);
    }

    public static QueryServicesClient getQueryServicesClient(String username, String password) {

        KieServicesConfiguration config = KieServicesFactory.newRestConfiguration(BASE_URL, username, password);
        List<String> capabilities = new ArrayList<String>();
        capabilities.add(KieServerConstants.CAPABILITY_BPM);
        config.setCapabilities(capabilities);
        
//        config.setMarshallingFormat(MarshallingFormat.JSON);
        config.setMarshallingFormat(MarshallingFormat.JAXB);

        
        KieServicesClient client = KieServicesFactory.newKieServicesClient(config);

        QueryServicesClient queryServiceClient = client.getServicesClient(QueryServicesClient.class);

        return queryServiceClient;
    }
    
    public static ProcessServicesClient getProcessServicesClient() {
        return getProcessServicesClient(DEFAULT_USERNAME, DEFAULT_PASSWORD);
    }

    public static ProcessServicesClient getProcessServicesClient(String username, String password) {

        KieServicesConfiguration config = KieServicesFactory.newRestConfiguration(BASE_URL, username, password);
        List<String> capabilities = new ArrayList<String>();
        capabilities.add(KieServerConstants.CAPABILITY_BPM);
        config.setCapabilities(capabilities);
        config.setMarshallingFormat(MarshallingFormat.JSON);
        KieServicesClient client = KieServicesFactory.newKieServicesClient(config);

        ProcessServicesClient processServiceClient = client.getServicesClient(ProcessServicesClient.class);

        return processServiceClient;
    }
    
    public static RuleServicesClient getRuleServicesClient() {
        return getRuleServicesClient(DEFAULT_USERNAME, DEFAULT_PASSWORD);
    }

    public static RuleServicesClient getRuleServicesClient(String username, String password) {

        KieServicesConfiguration config = KieServicesFactory.newRestConfiguration(BASE_URL, username, password);
        List<String> capabilities = new ArrayList<String>();
        capabilities.add(KieServerConstants.CAPABILITY_BRM);
        capabilities.add(KieServerConstants.CAPABILITY_BPM);
        config.setCapabilities(capabilities);
        config.setTimeout(60000);
        KieServicesClient client = KieServicesFactory.newKieServicesClient(config);

        RuleServicesClient ruleServiceClient = client.getServicesClient(RuleServicesClient.class);

        return ruleServiceClient;
    }
}
