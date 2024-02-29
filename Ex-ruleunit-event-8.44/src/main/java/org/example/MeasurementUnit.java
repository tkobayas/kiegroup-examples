
package org.example;

import java.util.HashSet;
import java.util.Set;

import org.drools.ruleunits.api.DataSource;
import org.drools.ruleunits.api.DataStream;
import org.drools.ruleunits.api.RuleUnitData;

public class MeasurementUnit implements RuleUnitData {

    private final DataStream<Measurement> measurements;
    private final Set<String> controlSet = new HashSet<>();

    public MeasurementUnit() {
        this(DataSource.createStream());
    }

    public MeasurementUnit(DataStream<Measurement> measurements) {
        this.measurements = measurements;
    }

    public DataStream<Measurement> getMeasurements() {
        return measurements;
    }

    public Set<String> getControlSet() {
        return controlSet;
    }
}
