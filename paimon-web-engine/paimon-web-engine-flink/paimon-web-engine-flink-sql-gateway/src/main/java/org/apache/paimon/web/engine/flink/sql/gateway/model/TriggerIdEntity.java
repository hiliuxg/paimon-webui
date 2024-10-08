/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.paimon.web.engine.flink.sql.gateway.model;

import lombok.Getter;
import org.apache.flink.runtime.messages.webmonitor.InfoMessage;
import org.apache.flink.runtime.rest.messages.ResponseBody;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.annotation.JsonCreator;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.annotation.JsonProperty;

/** entity using for trigger id . */
@Getter
public class TriggerIdEntity implements ResponseBody, InfoMessage {

    public static final String FIELD_NAME_TRIGGER_ID = "request-id";

    @JsonProperty(FIELD_NAME_TRIGGER_ID)
    private String triggerId;

    @JsonCreator
    public TriggerIdEntity(@JsonProperty(FIELD_NAME_TRIGGER_ID) String triggerId) {
        this.triggerId = triggerId;
    }
}
