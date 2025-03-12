
package org.example;

import java.util.List;

import org.drools.ruleunits.api.RuleUnitInstance;
import org.drools.ruleunits.api.RuleUnitProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RuleMain {

    static final Logger LOG = LoggerFactory.getLogger(RuleMain.class);

    public static void main(String[] args) {
        System.out.println("RuleMain : start");
        RuleMain ruleMain = new RuleMain();
        ruleMain.work();
    }
    
    
    public void work() {
        LOG.info("Creating RuleUnit");
        MeasurementUnit measurementUnit = new MeasurementUnit();

        RuleUnitInstance<MeasurementUnit> instance = RuleUnitProvider.get().createRuleUnitInstance(measurementUnit);
        try {
            LOG.info("Insert data");
            measurementUnit.getMeasurements().add(new Measurement("color", "red"));
            measurementUnit.getMeasurements().add(new Measurement("color", "green"));
            measurementUnit.getMeasurements().add(new Measurement("color", "blue"));

            LOG.info("Run query. Rules are also fired");
            List<Measurement> queryResult = instance.executeQuery("FindColor").toList("$m");

            System.out.println("queryResult : " + queryResult);
        } finally {
            instance.close();
        }
    }
}