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

package org.apache.paimon.web.engine.flink.sql.gataway.client;

import org.apache.paimon.web.engine.flink.common.status.JobStatus;
import org.apache.paimon.web.engine.flink.sql.gateway.client.FlinkJobAction;
import org.apache.paimon.web.engine.flink.sql.gateway.client.SessionClusterClient;
import org.apache.paimon.web.engine.flink.sql.gateway.model.JobOverviewEntity;
import org.apache.paimon.web.engine.flink.sql.gateway.model.TriggerIdEntity;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SessionClusterClientTest {

    @Test
    public void testGetJobOverview() throws Exception {
        String host = "localhost";
        int port = 33293;
        String jobId = "dab6205c3625841d49f9d32a1bfdf4db";
        FlinkJobAction flinkJobAction = new SessionClusterClient(host, port);
        JobOverviewEntity jobOverview = flinkJobAction.jobOverview(jobId);
        Assert.assertEquals(jobOverview.getState(), JobStatus.FINISHED.getValue());
    }

    @Test
    public void testStopWithSavePoint() throws Exception {
        String host = "localhost";
        int port = 33293;
        String jobId = "dab6205c3625841d49f9d32a1bfdf4db";
        FlinkJobAction flinkJobAction = new SessionClusterClient(host, port);
        TriggerIdEntity idEntity = flinkJobAction.stopWithSavePoint(jobId);
        Assertions.assertNotNull(idEntity);
    }
}
