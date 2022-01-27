package org.example.kie.server.warmup;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.kie.api.KieServices;
import org.kie.api.command.BatchExecutionCommand;
import org.kie.api.command.Command;
import org.kie.api.command.KieCommands;
import org.kie.api.runtime.ExecutionResults;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.internal.command.CommandFactory;
import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.api.model.KieContainerResource;
import org.kie.server.api.model.KieServerConfigItem;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.kie.server.client.RuleServicesClient;
import org.kie.server.services.api.KieContainerInstance;
import org.kie.server.services.api.KieServer;
import org.kie.server.services.api.KieServerEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyKieServerWarmupEventListenerVariant implements KieServerEventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyKieServerWarmupEventListenerVariant.class);

    public static final String KIE_WARMUP_LISTENER_STRATEGY = "org.example.kie.server.warmup.strategy";

    public static final String BASE_URL = "http://localhost:8080/kie-server/services/rest/server";
    private static final String USERNAME = "kieserver";
    private static final String PASSWORD = "kieserver1!";

    @Override
    public void beforeServerStarted(KieServer kieServer) {}

    @Override
    public void afterServerStarted(KieServer kieServer) {}

    @Override
    public void beforeServerStopped(KieServer kieServer) {}

    @Override
    public void afterServerStopped(KieServer kieServer) {}

    @Override
    public void beforeContainerStarted(KieServer kieServer, KieContainerInstance containerInstance) {}

    @Override
    public void afterContainerStarted(KieServer kieServer, KieContainerInstance containerInstance) {
        KieContainerResource resource = containerInstance.getResource();
        Optional<KieServerConfigItem> optItem = resource.getConfigItems().stream()
                                                        .filter(item -> item.getName().equals(KIE_WARMUP_LISTENER_STRATEGY))
                                                        .findFirst();
        if (!optItem.isPresent()) {
            LOGGER.info("{} is not enabled : ", KIE_WARMUP_LISTENER_STRATEGY);
            return;
        }
        String strategy = optItem.get().getValue();
        LOGGER.info("start warmup : " + strategy);
        long start = System.currentTimeMillis();
        switch (strategy) {
            case "rest-full":
                restWarmupFull(containerInstance);
                break;
            case "rest-fire-only":
                restWarmupFireOnly(containerInstance);
                break;
            case "internal-full":
                internalWarmupFull(containerInstance);
                break;
            case "internal-fire-only":
                internalWarmupFireOnly(containerInstance);
                break;
            default:
                break;
        }

        LOGGER.info("afterContainerStarted : elapsed time = {}ms", System.currentTimeMillis() - start);
    }

    private void internalWarmupFireOnly(KieContainerInstance containerInstance) {
        LOGGER.info("  internalWarmupFireOnly");
        KieContainer kieContainer = containerInstance.getKieContainer();

        StatelessKieSession statelessKieSession = kieContainer.newStatelessKieSession("myStatelessKsession");
        List<Command> cmds = new ArrayList<Command>();
        cmds.add(CommandFactory.newFireAllRules());
        BatchExecutionCommand batchExecutionCommand = CommandFactory.newBatchExecution(cmds);
        ExecutionResults batchResult = (ExecutionResults) statelessKieSession.execute(batchExecutionCommand);
        LOGGER.info("  batchResult = {}", batchResult);

        statelessKieSession = kieContainer.newStatelessKieSession("myStatelessKsession2");
        cmds = new ArrayList<Command>();
        cmds.add(CommandFactory.newFireAllRules());
        batchExecutionCommand = CommandFactory.newBatchExecution(cmds);
        batchResult = (ExecutionResults) statelessKieSession.execute(batchExecutionCommand);
        LOGGER.info("  batchResult = {}", batchResult);
    }

    private void internalWarmupFull(KieContainerInstance containerInstance) {
        LOGGER.info("  internalWarmupFull");
        KieContainer kieContainer = containerInstance.getKieContainer();

        Object person = null;
        Class<?> personClass = null;
        try {
            personClass = Class.forName("com.sample.Person", true, kieContainer.getClassLoader());
            if (personClass != null) {
                Constructor<?> constructor = personClass.getConstructor(String.class, int.class);
                person = constructor.newInstance("George", 18);
                LOGGER.info("  obj = {}", person);
            }
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }

        StatelessKieSession statelessKieSession = kieContainer.newStatelessKieSession("myStatelessKsession");
        List<Command> cmds = new ArrayList<Command>();
        cmds.add(CommandFactory.newInsert(Integer.valueOf(20), "integer-fact"));
        cmds.add(CommandFactory.newFireAllRules());
        BatchExecutionCommand batchExecutionCommand = CommandFactory.newBatchExecution(cmds);
        ExecutionResults batchResult = (ExecutionResults) statelessKieSession.execute(batchExecutionCommand);
        LOGGER.info("  batchResult = {}", batchResult);

        statelessKieSession = kieContainer.newStatelessKieSession("myStatelessKsession2");
        cmds = new ArrayList<Command>();
        cmds.add(CommandFactory.newInsert(person, "person-fact"));
        cmds.add(CommandFactory.newFireAllRules());
        batchExecutionCommand = CommandFactory.newBatchExecution(cmds);
        batchResult = (ExecutionResults) statelessKieSession.execute(batchExecutionCommand);
        LOGGER.info("  batchResult = {}", batchResult);
    }

    private void restWarmupFull(KieContainerInstance containerInstance) {
        LOGGER.info("  restWarmupFull");
        KieContainer kieContainer = containerInstance.getKieContainer();

        Object obj = null;
        Class<?> personClass = null;
        try {
            personClass = Class.forName("com.sample.Person", true, kieContainer.getClassLoader());
            if (personClass != null) {
                Constructor<?> constructor = personClass.getConstructor(String.class, int.class);
                obj = constructor.newInstance("George", 18);
                LOGGER.info("  obj = {}", obj);
            }
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }

        final Object person = obj;

        KieServicesConfiguration config = KieServicesFactory.newRestConfiguration(BASE_URL, USERNAME, PASSWORD);
        HashSet<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(personClass);
        config.addExtraClasses(classes);
        config.setMarshallingFormat(MarshallingFormat.JSON);

        KieServicesClient client = KieServicesFactory.newKieServicesClient(config);
        RuleServicesClient ruleClient = client.getServicesClient(RuleServicesClient.class);

        // myStatelessKsession
        List<Command<?>> commands = new ArrayList<Command<?>>();
        KieCommands commandsFactory = KieServices.Factory.get().getCommands();
        commands.add(commandsFactory.newInsert(Integer.valueOf(10), "fact-10"));
        commands.add(commandsFactory.newFireAllRules("fire-result"));
        BatchExecutionCommand batchExecution = commandsFactory.newBatchExecution(commands, "myStatelessKsession");
        ServiceResponse<ExecutionResults> response = ruleClient.executeCommandsWithResults(containerInstance.getContainerId(), batchExecution);
        LOGGER.info("  response = {}", response);

        // myStatelessKsession2
        commands = new ArrayList<Command<?>>();
        commands.add(commandsFactory.newInsert(person, "person-george"));
        commands.add(commandsFactory.newFireAllRules("fire-result"));
        batchExecution = commandsFactory.newBatchExecution(commands, "myStatelessKsession2");
        response = ruleClient.executeCommandsWithResults(containerInstance.getContainerId(), batchExecution);
        LOGGER.info("  response = {}", response);
    }

    private void restWarmupFireOnly(KieContainerInstance containerInstance) {
        LOGGER.info("  restWarmupFireOnly");
        KieServicesConfiguration config = KieServicesFactory.newRestConfiguration(BASE_URL, USERNAME, PASSWORD);
        config.setMarshallingFormat(MarshallingFormat.JSON);

        KieServicesClient client = KieServicesFactory.newKieServicesClient(config);
        RuleServicesClient ruleClient = client.getServicesClient(RuleServicesClient.class);

        // myStatelessKsession
        List<Command<?>> commands = new ArrayList<Command<?>>();
        KieCommands commandsFactory = KieServices.Factory.get().getCommands();
        commands.add(commandsFactory.newFireAllRules("fire-result"));
        BatchExecutionCommand batchExecution = commandsFactory.newBatchExecution(commands, "myStatelessKsession");
        ServiceResponse<ExecutionResults> response = ruleClient.executeCommandsWithResults(containerInstance.getContainerId(), batchExecution);
        LOGGER.info("  response = {}", response);

        // myStatelessKsession2
        commands = new ArrayList<Command<?>>();
        commands.add(commandsFactory.newFireAllRules("fire-result"));
        batchExecution = commandsFactory.newBatchExecution(commands, "myStatelessKsession2");
        response = ruleClient.executeCommandsWithResults(containerInstance.getContainerId(), batchExecution);
        LOGGER.info("  response = {}", response);
    }

    @Override
    public void beforeContainerStopped(KieServer kieServer, KieContainerInstance containerInstance) {}

    @Override
    public void afterContainerStopped(KieServer kieServer, KieContainerInstance containerInstance) {}

    @Override
    public void beforeContainerActivated(KieServer kieServer, KieContainerInstance containerInstance) {}

    @Override
    public void afterContainerActivated(KieServer kieServer, KieContainerInstance containerInstance) {}

    @Override
    public void beforeContainerDeactivated(KieServer kieServer, KieContainerInstance containerInstance) {}

    @Override
    public void afterContainerDeactivated(KieServer kieServer, KieContainerInstance containerInstance) {}
}
