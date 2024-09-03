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
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

/** entity using for job overview . */
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class JobOverviewEntity implements ResponseBody, InfoMessage {

    public static final String FIELD_NAME_JID = "jid";
    public static final String FIELD_NAME_NAME = "name";
    public static final String FIELD_NAME_IS_STOP_ABLE = "isStoppable";
    public static final String FIELD_NAME_STATE = "state";
    public static final String FIELD_NAME_START_TIME = "start-time";
    public static final String FIELD_NAME_END_TIME = "end-time";
    public static final String FIELD_NAME_DURATION = "duration";
    public static final String FIELD_NAME_MAX_PARALLELISM = "maxParallelism";
    public static final String FIELD_NAME_NOW = "now";
    private static final String FIELD_NAME_TIMESTAMP = "timestamp";
    private static final String FIELD_NAME_VERTICES = "vertices";
    private static final String FIELD_NAME_STATUS_COUNTS = "status-counts";
    private static final String FIELD_NAME_PLAN = "plan";

    @JsonProperty(FIELD_NAME_JID)
    private String jid;

    @JsonProperty(FIELD_NAME_NAME)
    private String name;

    @JsonProperty(FIELD_NAME_IS_STOP_ABLE)
    private Boolean isStoppable;

    @JsonProperty(FIELD_NAME_STATE)
    private String state;

    @JsonProperty(FIELD_NAME_START_TIME)
    private Long startTime;

    @JsonProperty(FIELD_NAME_END_TIME)
    private Long endTime;

    @JsonProperty(FIELD_NAME_DURATION)
    private Long duration;

    @JsonProperty(FIELD_NAME_MAX_PARALLELISM)
    private Integer maxParallelism;

    @JsonProperty(FIELD_NAME_NOW)
    private Long now;

    @JsonProperty(FIELD_NAME_TIMESTAMP)
    private Map<String, Long> timestamps;

    @JsonProperty(FIELD_NAME_VERTICES)
    private List<Object> vertices;

    @JsonProperty(FIELD_NAME_STATUS_COUNTS)
    private Map<String, Long> statusCounts;

    @JsonProperty(FIELD_NAME_PLAN)
    private Object plan;

    @JsonCreator
    public JobOverviewEntity(
            @JsonProperty(FIELD_NAME_JID) String jid,
            @JsonProperty(FIELD_NAME_NAME) String name,
            @JsonProperty(FIELD_NAME_IS_STOP_ABLE) Boolean isStoppable,
            @JsonProperty(FIELD_NAME_STATE) String state,
            @JsonProperty(FIELD_NAME_START_TIME) Long startTime,
            @JsonProperty(FIELD_NAME_END_TIME) Long endTime,
            @JsonProperty(FIELD_NAME_DURATION) Long duration,
            @JsonProperty(FIELD_NAME_MAX_PARALLELISM) Integer maxParallelism,
            @JsonProperty(FIELD_NAME_NOW) Long now,
            @JsonProperty(FIELD_NAME_TIMESTAMP) Map<String, Long> timestamps,
            @JsonProperty(FIELD_NAME_VERTICES) List<Object> vertices,
            @JsonProperty(FIELD_NAME_STATUS_COUNTS) Map<String, Long> statusCounts,
            @JsonProperty(FIELD_NAME_PLAN) Object plan) {

        this.jid = jid;
        this.name = name;
        this.isStoppable = isStoppable;
        this.state = state;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
        this.maxParallelism = maxParallelism;
        this.now = now;
        this.timestamps = timestamps;
        this.vertices = vertices;
        this.statusCounts = statusCounts;
        this.plan = plan;
    }
}
