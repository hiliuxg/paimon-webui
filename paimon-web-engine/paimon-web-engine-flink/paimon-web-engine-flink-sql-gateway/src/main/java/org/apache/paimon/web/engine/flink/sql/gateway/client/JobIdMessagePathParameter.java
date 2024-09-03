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

import org.apache.flink.runtime.rest.messages.ConversionException;
import org.apache.flink.runtime.rest.messages.MessagePathParameter;

public class JobIdMessagePathParameter extends MessagePathParameter<String> {

    public static final String KEY = "jobid";

    protected JobIdMessagePathParameter() {
        super(KEY);
    }

    @Override
    protected String convertFromString(String jobid) throws ConversionException {
        return jobid;
    }

    @Override
    protected String convertToString(String jobid) {
        return jobid;
    }

    @Override
    public String getDescription() {
        return "the id of flink job";
    }
}
