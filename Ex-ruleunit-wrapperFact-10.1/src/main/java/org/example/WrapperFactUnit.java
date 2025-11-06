/*
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

import java.util.ArrayList;
import java.util.List;

import org.drools.ruleunits.api.DataSource;
import org.drools.ruleunits.api.DataStore;
import org.drools.ruleunits.api.RuleUnitData;

public class WrapperFactUnit implements RuleUnitData {
    private final List<String> results = new ArrayList<>();
    private final DataStore<WrapperFact> wrapperFacts;

    public WrapperFactUnit() {
        this(DataSource.createStore());
    }

    public WrapperFactUnit(DataStore<WrapperFact> wrapperFacts) {
        this.wrapperFacts = wrapperFacts;
    }

    public DataStore<WrapperFact> getWrapperFacts() {
        return wrapperFacts;
    }

    public List<String> getResults() {
        return results;
    }
}
