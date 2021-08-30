package com.sample;

import org.drools.core.reteoo.ReteDumper;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.kogito.queries.Applicant;
import org.kie.kogito.queries.LoanApplication;

public class DroolsTest {

    public static void main(String[] args) {

        System.setProperty("drools.dump.dir", "/home/tkobayas/tmp");

        KieServices ks = KieServices.Factory.get();
        KieContainer kcontainer = ks.getKieClasspathContainer();
        KieBase kbase = kcontainer.getKieBase();
        KieSession ksession = kbase.newKieSession();
        ksession.setGlobal("maxAmount", 5000);

//        ReteDumper.dumpRete(ksession);

        Applicant applicant = new Applicant("John", 45);
        LoanApplication loanApplication = new LoanApplication("ABC10001", applicant, 2000, 100);

        ksession.insert(loanApplication);

        ksession.fireAllRules();

        QueryResults results = ksession.getQueryResults("FindApproved");
        System.out.println(results.toList());
        
        ksession.dispose();

    }
}
