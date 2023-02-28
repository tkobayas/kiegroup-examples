package com.sample;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.command.BatchExecutionCommand;
import org.kie.api.command.Command;
import org.kie.api.runtime.ExecutionResults;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.internal.command.CommandFactory;

public class DroolsTest {

    @Test
    public void testRules() {
        
        //System.setProperty("drools.sequential", "true");
        
        KieServices ks = KieServices.Factory.get();
        KieContainer kcontainer = ks.getKieClasspathContainer();
        KieBase kbase = kcontainer.getKieBase();
        StatelessKieSession ksession = kbase.newStatelessKieSession();

        Person john = new Person("john", 17);
        final List<Command> cmds = new ArrayList<Command>();
        cmds.add(CommandFactory.newInsert(john, "john"));
        BatchExecutionCommand batchExecutionCommand = CommandFactory.newBatchExecution(cmds);

        final ExecutionResults batchResult = (ExecutionResults) ksession.execute(batchExecutionCommand);

        batchResult.getIdentifiers().stream().forEach(s -> System.out.println(batchResult.getValue(s)));
    }
}
