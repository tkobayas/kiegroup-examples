
package org.example;

import java.util.HashSet;
import java.util.Set;

import org.drools.ruleunits.api.DataSource;
import org.drools.ruleunits.api.DataStore;
import org.drools.ruleunits.api.RuleUnitData;

public class MeasurementUnit implements RuleUnitData {

    private final DataStore<Measurement> measurements;
    private final DataStore<Sensor> sensors;
    private final Set<String> controlSet = new HashSet<>();

    public MeasurementUnit() {
        this(DataSource.createStore(), DataSource.createStore());
    }

    public MeasurementUnit(DataStore<Measurement> measurements, DataStore<Sensor> sensors) {
        this.measurements = measurements;
        this.sensors = sensors;
    }

    public DataStore<Measurement> getMeasurements() {
        return measurements;
    }

    public DataStore<Sensor> getSensors() {
        return sensors;
    }

    public Set<String> getControlSet() {
        return controlSet;
    }
}
