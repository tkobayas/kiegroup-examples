
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
        ExampleUnit exampleUnit = new ExampleUnit();

        RuleUnitInstance<ExampleUnit> instance = RuleUnitProvider.get().createRuleUnitInstance(exampleUnit);
        try {
            LOG.info("Insert data");
            exampleUnit.getVariables().add(new Variable("id001"));
            exampleUnit.getVariables().add(new Variable("id002"));

            LOG.info("Fire rules");
            instance.fire();

            LOG.info("Run query");
            List<String> queryResult = instance.executeQuery("GetStrings").toList("$result");
            System.out.println("queryResult : " + queryResult);

        } finally {
            instance.close();
        }
    }
}
