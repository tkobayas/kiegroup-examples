package com.sample;

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

import static com.sample.Constants.BASE_URL;
import static com.sample.Constants.PASSWORD;
import static com.sample.Constants.USERNAME;

public class KieServerRestUtils {

    public static KieServicesClient getKieServicesClient(String username, String password) {

        KieServicesConfiguration config = KieServicesFactory.newRestConfiguration(BASE_URL, username, password);
        List<String> capabilities = new ArrayList<String>();
        capabilities.add(KieServerConstants.CAPABILITY_BPM);
        config.setCapabilities(capabilities);
        KieServicesClient client = KieServicesFactory.newKieServicesClient(config);

        return client;
    }

    public static UserTaskServicesClient getUserTaskServicesClient() {
        return getUserTaskServicesClient(USERNAME, PASSWORD);
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
        return getQueryServicesClient(USERNAME, PASSWORD);
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
        return getProcessServicesClient(USERNAME, PASSWORD);
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
        return getRuleServicesClient(USERNAME, PASSWORD);
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
