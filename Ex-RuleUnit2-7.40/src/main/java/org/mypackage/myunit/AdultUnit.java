package org.mypackage.myunit;

import org.drools.ruleunit.DataSource;
import org.drools.ruleunit.RuleUnit;

public class AdultUnit implements RuleUnit {

    private int adultAge;
    private DataSource<Person> persons;

    public AdultUnit() {}

    public AdultUnit(DataSource<Person> persons, int adultAge) {
        this.persons = persons;
        this.adultAge = adultAge;
    }

    // A data source of `Persons` in this rule unit:
    public DataSource<Person> getPersons() {
        return persons;
    }

    // A global variable in this rule unit:
    public int getAdultAge() {
        return adultAge;
    }

    // Life-cycle methods:
    @Override
    public void onStart() {
        System.out.println("AdultUnit started.");
    }

    @Override
    public void onEnd() {
        System.out.println("AdultUnit ended.");
    }
}
