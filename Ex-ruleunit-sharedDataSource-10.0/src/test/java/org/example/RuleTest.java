/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.example;

import java.util.List;

import org.drools.ruleunits.api.DataObserver;
import org.drools.ruleunits.api.DataSource;
import org.drools.ruleunits.api.DataStore;
import org.drools.ruleunits.api.RuleUnitProvider;
import org.drools.ruleunits.api.RuleUnitInstance;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RuleTest {

    static final Logger LOG = LoggerFactory.getLogger(RuleTest.class);

    @Test
    public void test() {
        LOG.info("Creating RuleUnit");
        DataStore<Measurement> sharedDataStore = DataSource.createStore();

        Unit1 unit1 = new Unit1(sharedDataStore);
        Unit2 unit2 = new Unit2(sharedDataStore);

        LOG.info("insert facts");
        sharedDataStore.add(new Measurement("color", "red"));
        sharedDataStore.add(new Measurement("color", "green"));
        sharedDataStore.add(new Measurement("color", "blue"));

        LOG.info("1st RuleUnit");
        try (RuleUnitInstance<Unit1> instance1 = RuleUnitProvider.get().createRuleUnitInstance(unit1)) {
            instance1.fire();
        }

        LOG.info("2nd RuleUnit");
        try (RuleUnitInstance<Unit2> instance2 = RuleUnitProvider.get().createRuleUnitInstance(unit2)) {
            instance2.fire();
        }
    }
}