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

package org.apache.paimon.web.api.action.context.options;

/** FlinkCdcOptions. */
public class FlinkCdcOptions {

    private FlinkCdcOptions() {}

    public static final String MYSQL_CONF = "mysql_conf";

    public static final String POSTGRES_CONF = "postgres_conf";

    public static final String TABLE_CONF = "table_conf";

    public static final String WAREHOUSE = "warehouse";

    public static final String DATABASE = "database";

    public static final String TABLE = "table";

    public static final String PARTITION_KEYS = "partition_keys";

    public static final String PRIMARY_KEYS = "primary_keys";

    public static final String COMPUTED_COLUMN = "computed_column";

    public static final String METADATA_COLUMN = "metadata_column";

    public static final String SESSION_URL = "sessionUrl";

    public static final String CATALOG_CONF = "catalog_conf";

    public static final String PIPELINE_NAME = "pipeline.name";

    public static final String EXE_CP_INTERVAL = "execution.checkpointing.interval";
}
