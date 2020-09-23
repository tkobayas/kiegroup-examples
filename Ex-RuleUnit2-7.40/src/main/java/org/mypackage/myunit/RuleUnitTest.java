package org.mypackage.myunit;

import org.drools.ruleunit.DataSource;
import org.drools.ruleunit.RuleUnit;
import org.drools.ruleunit.RuleUnitExecutor;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;

public class RuleUnitTest {

    public static void main(String[] args) {

        KieServices ks = KieServices.Factory.get();
        KieContainer kieContainer = ks.getKieClasspathContainer();

        // Create a `RuleUnitExecutor` class and bind it to the KIE base:
        KieBase kbase = kieContainer.getKieBase();
        RuleUnitExecutor executor = RuleUnitExecutor.create().bind(kbase);

        DataSource<Person> persons = DataSource.create( new Person( "Mario", 42 ),
                                                        new Person( "Marilena", 44 ),
                                                        new Person( "Sofia", 4 ) );

        
        // Create the `AdultUnit` rule unit using the `persons` data source and run the executor:
        RuleUnit adultUnit = new AdultUnit(persons, 18);
        executor.run(adultUnit);

    }
}
