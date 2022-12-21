
package org.example;

import java.util.List;

import org.drools.ruleunits.api.RuleUnitInstance;
import org.drools.ruleunits.api.RuleUnitProvider;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RuleTest {

    static final Logger LOG = LoggerFactory.getLogger(RuleTest.class);

    @Test
    public void test() {
        LOG.info("Creating RuleUnit");
        LoanUnit loanUnit = new LoanUnit();
        loanUnit.setMaxAmount(5000);

        RuleUnitInstance<LoanUnit> instance = RuleUnitProvider.get().createRuleUnitInstance(loanUnit);
        try {
            LOG.info("Insert data");
            Applicant applicant1 = new Applicant("John", 30);
            LoanApplication loanApplication1 = new LoanApplication("A001", applicant1, 2000, 100);
            loanUnit.getLoanApplications().add(loanApplication1);
            Applicant applicant2 = new Applicant("Paul", 29);
            LoanApplication loanApplication2 = new LoanApplication("A002", applicant2, 5000, 100);
            loanUnit.getLoanApplications().add(loanApplication2);
            Applicant applicant3 = new Applicant("George", 27);
            LoanApplication loanApplication3 = new LoanApplication("A002", applicant3, 5000, 1000);
            loanUnit.getLoanApplications().add(loanApplication3);


            LOG.info("Run query. Rules are also fired");
            List<LoanApplication> queryResult = instance.executeQuery("FindApproved").toList("$l");

            System.out.println("approved : " + queryResult);
        } finally {
            instance.close();
        }
    }
}