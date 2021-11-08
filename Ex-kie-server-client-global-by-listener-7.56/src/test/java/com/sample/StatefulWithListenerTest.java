package com.sample;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.myspace.myproj.Person;
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

import static com.sample.Constants.BASE_URL;
import static com.sample.Constants.CONTAINER_ID;

public class StatefulWithListenerTest extends TestCase {

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
        Person p1 = new Person();
        p1.setName("John");
        p1.setAge(20);
        runClient(p1);

        // 2nd run
        Person p2 = new Person();
        p2.setName("Paul");
        p2.setAge(10);
        runClient(p2);

        // don't forget dispose
        dispose();
    }

    private void setup() {
        System.out.println("-----------------------------------");
        System.out.println("Setup Global 'results' by listner triggered by a String");

        RuleServicesClient ruleClient = getRuleClient();

        List<Command<?>> commands = new ArrayList<Command<?>>();
        KieCommands commandsFactory = KieServices.Factory.get().getCommands();

        commands.add(commandsFactory.newInsert("reset", "fact-reset"));

        BatchExecutionCommand batchExecution = commandsFactory.newBatchExecution(commands); // lookup default stateful ksession

        ServiceResponse<ExecutionResults> response = ruleClient.executeCommandsWithResults(CONTAINER_ID, batchExecution);

        System.out.println(response);

        List results = (List) response.getResult().getValue(GLOBAL_IDENTIFIER);

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
        System.out.println("-----------------------------------");
        System.out.println("Send : " + person);

        RuleServicesClient ruleClient = getRuleClient();

        List<Command<?>> commands = new ArrayList<Command<?>>();
        KieCommands commandsFactory = KieServices.Factory.get().getCommands();

        commands.add(commandsFactory.newInsert(person, "fact-" + person.getName()));

        commands.add(commandsFactory.newFireAllRules("fire-result"));
        commands.add(commandsFactory.newGetGlobal(GLOBAL_IDENTIFIER));

        BatchExecutionCommand batchExecution = commandsFactory.newBatchExecution(commands); // lookup default stateful ksession

        ServiceResponse<ExecutionResults> response = ruleClient.executeCommandsWithResults(CONTAINER_ID, batchExecution);

        System.out.println(response);

        List results = (List) response.getResult().getValue(GLOBAL_IDENTIFIER);

        System.out.println("results = " + results);
    }

    private void dispose() {
        System.out.println("-----------------------------------");
        System.out.println("Dispose");

        RuleServicesClient ruleClient = getRuleClient();

        List<Command<?>> commands = new ArrayList<Command<?>>();
        KieCommands commandsFactory = KieServices.Factory.get().getCommands();

        commands.add(commandsFactory.newDispose());

        BatchExecutionCommand batchExecution = commandsFactory.newBatchExecution(commands);

        ServiceResponse<ExecutionResults> response = ruleClient.executeCommandsWithResults(CONTAINER_ID, batchExecution);

        System.out.println(response);
    }

}
