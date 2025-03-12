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

public class StatelessTest extends TestCase {

    private static final String USERNAME = "kieserver";
    private static final String PASSWORD = "kieserver1!";

    private static final String KSESSION_NAME = "myStatelessKsession";

    private static final String GLOBAL_IDENTIFIER = "results";

    @Test
    public void testRule() {
        KieServicesConfiguration config = KieServicesFactory.newRestConfiguration(BASE_URL, USERNAME, PASSWORD);
        HashSet<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(Person.class);
        config.addExtraClasses(classes);

        KieServicesClient client = KieServicesFactory.newKieServicesClient(config);
        RuleServicesClient ruleClient = client.getServicesClient(RuleServicesClient.class);

        List<Command<?>> commands = new ArrayList<Command<?>>();
        KieCommands commandsFactory = KieServices.Factory.get().getCommands();

        Person person = new Person("John", 36);

        commands.add(commandsFactory.newSetGlobal(GLOBAL_IDENTIFIER, new ArrayList<>()));
        commands.add(commandsFactory.newInsert(person, "fact-" + person.getName()));
        commands.add(commandsFactory.newFireAllRules("fire-result"));
        commands.add(commandsFactory.newGetGlobal(GLOBAL_IDENTIFIER));

        BatchExecutionCommand batchExecution = commandsFactory.newBatchExecution(commands, KSESSION_NAME);

        ServiceResponse<ExecutionResults> response = ruleClient.executeCommandsWithResults(CONTAINER_ID, batchExecution);

        System.out.println("-----------------------------------");
        System.out.println(response);

        List<Object> results = (List<Object>) response.getResult().getValue(GLOBAL_IDENTIFIER);
        System.out.println("results = " + results);

        // No need to dispose stateless ksession
    }

}
