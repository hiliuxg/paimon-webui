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

package org.apache.paimon.web.engine.flink.sql.gateway.client;

import org.apache.paimon.web.engine.flink.sql.gateway.model.TriggerIdEntity;

import org.apache.flink.runtime.rest.HttpMethodWrapper;
import org.apache.flink.runtime.rest.messages.EmptyRequestBody;
import org.apache.flink.runtime.rest.messages.RuntimeMessageHeaders;
import org.apache.flink.shaded.netty4.io.netty.handler.codec.http.HttpResponseStatus;

public class StopWithSavePointHeaders
        implements RuntimeMessageHeaders<
                EmptyRequestBody, TriggerIdEntity, JobIdMessageParameters> {

    private final String jobId;
    public static final String URL = "/jobs/:" + JobIdMessagePathParameter.KEY + "/stop";

    public StopWithSavePointHeaders(String jobId) {
        this.jobId = jobId;
    }

    @Override
    public Class<TriggerIdEntity> getResponseClass() {
        return TriggerIdEntity.class;
    }

    @Override
    public HttpResponseStatus getResponseStatusCode() {
        return HttpResponseStatus.OK;
    }

    @Override
    public String getDescription() {
        return "Stops a job with a savepoint. Optionally, it can also emit a MAX_WATERMARK before taking the savepoint"
                + " to flush out any state waiting for timers to fire. This async operation would return a 'triggerid' "
                + "for further query identifier.";
    }

    @Override
    public Class<EmptyRequestBody> getRequestClass() {
        return EmptyRequestBody.class;
    }

    @Override
    public JobIdMessageParameters getUnresolvedMessageParameters() {
        return new JobIdMessageParameters(jobId);
    }

    @Override
    public HttpMethodWrapper getHttpMethod() {
        return HttpMethodWrapper.POST;
    }

    @Override
    public String getTargetRestEndpointURL() {
        return URL;
    }
}
