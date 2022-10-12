package com.sample;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.company.model.Model;
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
import static com.sample.Constants.PASSWORD;
import static com.sample.Constants.USERNAME;

public class StatefulTest extends TestCase {

    @Test
    public void testRule() {
        KieServicesConfiguration config = KieServicesFactory.newRestConfiguration(BASE_URL, USERNAME, PASSWORD);
        HashSet<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(Model.class);
        config.addExtraClasses(classes);

        KieServicesClient client = KieServicesFactory.newKieServicesClient(config);
        RuleServicesClient ruleClient = client.getServicesClient(RuleServicesClient.class);

        List<Command<?>> commands = new ArrayList<Command<?>>();
        KieCommands commandsFactory = KieServices.Factory.get().getCommands();

        Model model = new Model();
        model.setValue("Model!!");

        commands.add(commandsFactory.newInsert(model, "fact1"));
        commands.add(commandsFactory.newFireAllRules());

        BatchExecutionCommand batchExecution = commandsFactory.newBatchExecution(commands);

        ServiceResponse<ExecutionResults> response = ruleClient.executeCommandsWithResults(CONTAINER_ID, batchExecution);

        System.out.println("-----------------------------------");
        System.out.println(response);

        // Dispose stateful kssesion
        commands = new ArrayList<Command<?>>();
        commands.add(commandsFactory.newDispose());
        batchExecution = commandsFactory.newBatchExecution(commands);

        response = ruleClient.executeCommandsWithResults(CONTAINER_ID, batchExecution);

        System.out.println("-----------------------------------");
        System.out.println(response);
    }

}
