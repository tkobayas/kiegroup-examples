package com.example.demo;

import org.drools.ruleunits.api.RuleUnitInstance;
import org.drools.ruleunits.api.RuleUnitProvider;
import org.springframework.stereotype.Component;

@Component
public class HelloService {

    public String hello(String name) {
        HelloJarUnit unit = new HelloJarUnit();
        unit.getStrings().add("Hello " + name);

        try (RuleUnitInstance<HelloJarUnit> unitInstance = RuleUnitProvider.get().createRuleUnitInstance(unit)) {
            unitInstance.fire();
            System.out.println("results = " + unit.getResults());
        }
        return unit.getResults().get(0);
    }
}
