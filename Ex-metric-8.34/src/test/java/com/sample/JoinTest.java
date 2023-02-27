package com.sample;

import java.util.ArrayList;
import java.util.List;

import org.drools.core.reteoo.ReteDumper;
import org.junit.Test;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import static org.junit.Assert.assertEquals;

public class JoinTest {

    private final int NUM_CUSTOMER = 10;
    private final int NUM_ORDER_PER_CUSTOMER = 100;

    @Test
    public void testJoin() {

        System.setProperty("drools.metric.logger.enabled", "true");
        System.setProperty("drools.metric.logger.threshold", "500"); // microseconds

        KieServices ks = KieServices.Factory.get();
        KieContainer kcontainer = ks.getKieClasspathContainer();
        KieBase kbase = kcontainer.getKieBase();
        KieSession ksession = kbase.newKieSession();
        List<List<Order>> result = new ArrayList<>();
        ksession.setGlobal("result", result);

        // convenient static methods
        System.out.println("======= dumpRete output");
        ReteDumper.dumpRete(kbase);
        System.out.println("=======");
        System.out.println();
        System.out.println("======= dumpAssociatedRulesRete output");
        ReteDumper.dumpAssociatedRulesRete(kbase);
        System.out.println("=======");

        System.out.println();

        for (int i = 0; i < NUM_CUSTOMER; i++) {
            Customer cust = new Customer("Customer" + i);
            ksession.insert(cust);
            for (int j = 0; j < NUM_ORDER_PER_CUSTOMER; j++) {
                long id = (i * NUM_ORDER_PER_CUSTOMER) + j;
                int price = j * 10;
                Order order = new Order(id, cust, "Item" + j, price);
                ksession.insert(order);
            }
        }

        System.out.println("-----------");

        long start = System.currentTimeMillis();
        ksession.fireAllRules();
        System.out.println("  -> elapsed time (ms) : " + (System.currentTimeMillis() - start));

        ksession.dispose();

        System.out.println("result.size() = " + result.size());
        assertEquals(100, result.size());
    }
}
