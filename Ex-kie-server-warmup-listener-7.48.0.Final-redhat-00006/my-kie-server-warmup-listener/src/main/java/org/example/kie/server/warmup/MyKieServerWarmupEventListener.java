package org.example.kie.server.warmup;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.kie.api.builder.model.KieSessionModel.KieSessionType;
import org.kie.api.command.BatchExecutionCommand;
import org.kie.api.command.Command;
import org.kie.api.runtime.ExecutionResults;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.internal.command.CommandFactory;
import org.kie.server.api.model.KieContainerResource;
import org.kie.server.api.model.KieServerConfigItem;
import org.kie.server.services.api.KieContainerInstance;
import org.kie.server.services.api.KieServer;
import org.kie.server.services.api.KieServerEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyKieServerWarmupEventListener implements KieServerEventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyKieServerWarmupEventListener.class);

    public static final String KIE_WARMUP_LISTENER_STRATEGY = "org.example.kie.server.warmup.strategy";

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

        // To set the KIE_WARMUP_LISTENER_STRATEGY value, see CreateContainerWithWarmupTrue.java in Ex-kie-server-client-7.48.0.Final-redhat-00006
        // If you comment-out this part, you can always trigger warm-up.
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

        try {
            switch (strategy) {
                case "internal-fire-only":
                    internalWarmupFireOnly(containerInstance);
                    break;
                case "internal-full":
                    // in order to use internalWarmupFull, you need to edit the method to insert expected fact objects
                    internalWarmupFull(containerInstance);
                    break;
                default:
                    break;
            }

        } catch (Exception e) {
            LOGGER.warn("warmup failed. You can ignore the error and use the deployed container", e);
        }

        LOGGER.info("afterContainerStarted : elapsed time = {}ms", System.currentTimeMillis() - start);
    }

    private void internalWarmupFireOnly(KieContainerInstance containerInstance) {
        LOGGER.info("  internalWarmupFireOnly");
        KieContainer kieContainer = containerInstance.getKieContainer();
        Collection<String> kieBaseNames = kieContainer.getKieBaseNames();

        // warmup all kieSessions
        kieBaseNames.stream()
                    .flatMap(kieBaseName -> kieContainer.getKieSessionNamesInKieBase(kieBaseName).stream())
                    .forEach(kieSessionName -> {
                        LOGGER.info("warmup : kieSessionName = {}", kieSessionName);
                        KieSessionType type = kieContainer.getKieSessionModel(kieSessionName).getType();
                        if (type == KieSessionType.STATEFUL) {
                            KieSession kieSession = kieContainer.newKieSession(kieSessionName);
                            kieSession.fireAllRules();
                            kieSession.dispose();
                        } else {
                            StatelessKieSession statelessKieSession = kieContainer.newStatelessKieSession(kieSessionName);
                            final List<Command> cmds = new ArrayList<Command>();
                            cmds.add(CommandFactory.newFireAllRules());
                            BatchExecutionCommand batchExecutionCommand = CommandFactory.newBatchExecution(cmds);
                            final ExecutionResults batchResult = (ExecutionResults) statelessKieSession.execute(batchExecutionCommand);
                        }
                    });
    }

    private void internalWarmupFull(KieContainerInstance containerInstance) {
        LOGGER.info("  internalWarmupFull");
        KieContainer kieContainer = containerInstance.getKieContainer();

        // you need to get the class via kieContainer's classloader. Unless, the fact wouldn't match the rule
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
            throw new RuntimeException(e);
        }

        // Note that these warm-up codes are tailer for each kieSession
        StatelessKieSession statelessKieSession = kieContainer.newStatelessKieSession("myStatelessKsession");
        List<Command> cmds = new ArrayList<Command>();
        cmds.add(CommandFactory.newInsert(Integer.valueOf(20), "integer-fact")); // You need to know what facts should be inserted
        cmds.add(CommandFactory.newFireAllRules());
        BatchExecutionCommand batchExecutionCommand = CommandFactory.newBatchExecution(cmds);
        ExecutionResults batchResult = (ExecutionResults) statelessKieSession.execute(batchExecutionCommand);
        LOGGER.info("  batchResult = {}", batchResult);

        statelessKieSession = kieContainer.newStatelessKieSession("myStatelessKsession2");
        cmds = new ArrayList<Command>();
        cmds.add(CommandFactory.newInsert(person, "person-fact")); // You need to know what facts should be inserted
        cmds.add(CommandFactory.newFireAllRules());
        batchExecutionCommand = CommandFactory.newBatchExecution(cmds);
        batchResult = (ExecutionResults) statelessKieSession.execute(batchExecutionCommand);
        LOGGER.info("  batchResult = {}", batchResult);
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
