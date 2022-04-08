/*
 * Copyright 2017 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sample;

import java.io.IOException;

import org.junit.Test;
import org.kie.server.controller.api.model.spec.ServerTemplateList;
import org.kie.server.controller.client.KieServerControllerClient;
import org.kie.server.controller.client.KieServerControllerClientFactory;

public class WebSocketControllerTest {
    
    private static final String CONTROLLER_MANAGEMENT_URL = "ws://localhost:8080/kie-server-controller-services/websocket/controller";

    private static final String USER = "yoda";
    private static final String PASSWORD = "usetheforce123@";

    @Test
    public void testCreateKieServerTemplate() throws IOException {

        KieServerControllerClient controllerClient = KieServerControllerClientFactory.newWebSocketClient(CONTROLLER_MANAGEMENT_URL,
                                                                                                         USER,
                                                                                                         PASSWORD);

        ServerTemplateList serverTemplates = controllerClient.listServerTemplates();
        System.out.println(serverTemplates);
        
        controllerClient.close();
    }

}
