
package org.example;

import org.drools.ruleunits.api.DataSource;
import org.drools.ruleunits.api.DataStore;
import org.drools.ruleunits.api.RuleUnitData;

public class ExampleUnit implements RuleUnitData {

    private final DataStore<Variable> variables;

    private final DataStore<String> strings;

    public ExampleUnit() {
        this(DataSource.createStore(), DataSource.createStore());
    }

    public ExampleUnit(DataStore<Variable> variables, DataStore<String> strings) {
        this.variables = variables;
        this.strings = strings;
    }

    public DataStore<Variable> getVariables() {
        return variables;
    }

    public DataStore<String> getStrings() {
        return strings;
    }

}
