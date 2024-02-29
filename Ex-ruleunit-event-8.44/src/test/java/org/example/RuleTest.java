
package org.example;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.drools.ruleunits.api.RuleUnitInstance;
import org.drools.ruleunits.api.RuleUnitProvider;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;

public class RuleTest {

    static final Logger LOG = LoggerFactory.getLogger(RuleTest.class);
    private static final int MAX_THREAD = 10;

    @Test
    public void test() {
        LOG.info("Creating RuleUnit");
        MeasurementUnit measurementUnit = new MeasurementUnit();

        RuleUnitInstance<MeasurementUnit> instance = RuleUnitProvider.get().createRuleUnitInstance(measurementUnit);
        try {

            ExecutorService executor = Executors.newFixedThreadPool(MAX_THREAD);

            for (int n = 0; n < MAX_THREAD; n++) {
                final int id = n;
                executor.execute(() -> {
                    measurementUnit.getMeasurements().append(new Measurement("color", "red" + id));
                    measurementUnit.getMeasurements().append(new Measurement("color", "green" + id));
                    measurementUnit.getMeasurements().append(new Measurement("color", "blue" + id));

                    // Unlike StatefulKnowledgeSession, RuleUnitInstance is a light-weight session, so not thread-safe.
                    // synchronized is required (As of Drools 8.44.0)
                    synchronized (instance) {
                        instance.fire();
                    }
                });
            }

            executor.shutdown();
            executor.awaitTermination(300, TimeUnit.SECONDS);

            Set<String> controlSet = measurementUnit.getControlSet();
            LOG.info("controlSet : " + controlSet);

            assertEquals(30, controlSet.size());

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            instance.close();
        }
    }
}