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

package org.apache.paimon.web.api.action.context;

import lombok.experimental.SuperBuilder;

import javax.annotation.Nullable;

import java.util.List;

/** The FlinkCdcTableSyncActionContext for the table synchronization. */
@SuperBuilder
public abstract class FlinkCdcTableSyncActionContext extends FlinkActionContext
        implements ActionContext {

    @ActionConf(value = "warehouse")
    protected String warehouse;

    @ActionConf(value = "database")
    protected String database;

    @ActionConf(value = "table")
    protected String table;

    @ActionConf("partition_keys")
    @Nullable
    protected String partitionKeys;

    @ActionConf("primary_keys")
    @Nullable
    protected String primaryKeys;

    @ActionConf("computed_column")
    @Nullable
    protected List<String> computedColumnList;

    @ActionConf("metadata_column")
    @Nullable
    protected String metaDataColumn;

    @ActionConf(value = "catalog_conf")
    @Nullable
    protected List<String> catalogConfList;

    @ActionConf(value = "table_conf")
    @Nullable
    protected List<String> tableConfList;
}
