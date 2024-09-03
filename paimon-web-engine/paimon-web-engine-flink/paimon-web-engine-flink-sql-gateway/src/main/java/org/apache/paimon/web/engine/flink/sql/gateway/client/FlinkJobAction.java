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

import org.apache.paimon.web.engine.flink.sql.gateway.model.JobOverviewEntity;
import org.apache.paimon.web.engine.flink.sql.gateway.model.TriggerIdEntity;

/** Using to execute flink job action. */
public interface FlinkJobAction {


    /**
     * Execute cluster action to get job overview.
     *
     * @param jobId the flink jobId
     * @return flink job entity.
     */
    JobOverviewEntity jobOverview(String jobId);

    /**
     * Stops a job with a savepoint.
     *
     * @param jobId the flink jobId
     * @return return a 'triggerId' for further query identifier.
     */
    TriggerIdEntity stopWithSavePoint(String jobId);


}
