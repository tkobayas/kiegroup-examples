package com.sample;

import static com.sample.Constants.BASE_URL;
import static com.sample.Constants.CONTAINER_ID;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import junit.framework.TestCase;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.command.BatchExecutionCommand;
import org.kie.api.command.Command;
import org.kie.api.command.KieCommands;
import org.kie.api.runtime.ExecutionResults;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.kie.server.client.RuleServicesClient;

public class StatefulTest extends TestCase {

    private static final String USERNAME = "kieserver";
    private static final String PASSWORD = "kieserver1!";
    
    private static final String KSESSION_NAME = "myStatefulKsession";
    
    private static final String GLOBAL_IDENTIFIER = "results";

    //    private static final MarshallingFormat FORMAT = MarshallingFormat.JSON;
    //    private static final MarshallingFormat FORMAT = MarshallingFormat.JAXB;

    @Test
    public void testProcess() {
        
        // You will always use the same Stateful ksession with lookup="myStatefulKsession" which you configured in kmodule.xml
        
        // set global
        setup(); // results = []

        // 1st run
        runClient(new Person("John", 20)); // results = [hit Hello rule]
        
        // 2nd run
        runClient(new Person("Paul", 18)); // results = [hit Hello rule, hit Hello rule]
        
        // don't forget dispose
        dispose();
    }

    private void setup() {
        RuleServicesClient ruleClient = getRuleClient();

        List<Command<?>> commands = new ArrayList<Command<?>>();
        KieCommands commandsFactory = KieServices.Factory.get().getCommands();

        commands.add(commandsFactory.newSetGlobal(GLOBAL_IDENTIFIER, new ArrayList())); // set global only once
        commands.add(commandsFactory.newGetGlobal(GLOBAL_IDENTIFIER));

        BatchExecutionCommand batchExecution = commandsFactory.newBatchExecution(commands);

        ServiceResponse<ExecutionResults> response = ruleClient.executeCommandsWithResults(CONTAINER_ID, batchExecution);

        System.out.println("-----------------------------------");

        System.out.println(response);

        List results = (List)response.getResult().getValue(GLOBAL_IDENTIFIER);

        System.out.println("results = " + results);
    }

    private RuleServicesClient getRuleClient() {
        KieServicesConfiguration config = KieServicesFactory.newRestConfiguration(BASE_URL, USERNAME, PASSWORD);
        HashSet<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(Person.class);

        config.addExtraClasses(classes);
        KieServicesClient client = KieServicesFactory.newKieServicesClient(config);

        RuleServicesClient ruleClient = client.getServicesClient(RuleServicesClient.class);
        return ruleClient;
    }
    
    private void runClient(Person person) {
        RuleServicesClient ruleClient = getRuleClient();

        List<Command<?>> commands = new ArrayList<Command<?>>();
        KieCommands commandsFactory = KieServices.Factory.get().getCommands();
        
        commands.add(commandsFactory.newInsert(person, "fact-" + person.getName()));

        commands.add(commandsFactory.newFireAllRules("fire-result"));
        commands.add(commandsFactory.newGetGlobal(GLOBAL_IDENTIFIER));

        BatchExecutionCommand batchExecution = commandsFactory.newBatchExecution(commands);

        ServiceResponse<ExecutionResults> response = ruleClient.executeCommandsWithResults(CONTAINER_ID, batchExecution);

        System.out.println("-----------------------------------");

        System.out.println(response);

        List results = (List)response.getResult().getValue(GLOBAL_IDENTIFIER);

        System.out.println("results = " + results);
    }
    
    private void dispose() {
        RuleServicesClient ruleClient = getRuleClient();

        List<Command<?>> commands = new ArrayList<Command<?>>();
        KieCommands commandsFactory = KieServices.Factory.get().getCommands();
        
        commands.add(commandsFactory.newDispose());

        BatchExecutionCommand batchExecution = commandsFactory.newBatchExecution(commands);

        ServiceResponse<ExecutionResults> response = ruleClient.executeCommandsWithResults(CONTAINER_ID, batchExecution);

        System.out.println("-----------------------------------");

        System.out.println(response);

    }

}