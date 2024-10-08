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

package org.apache.paimon.web.api.action.context.factory;

import org.apache.paimon.web.api.action.context.ActionContext;
import org.apache.paimon.web.api.action.context.ActionContextUtil;
import org.apache.paimon.web.api.action.context.MysqlSyncTableActionContext;
import org.apache.paimon.web.api.action.context.options.FlinkCdcOptions;
import org.apache.paimon.web.api.enums.FlinkCdcDataSourceType;
import org.apache.paimon.web.api.enums.FlinkCdcSyncType;
import org.apache.paimon.web.api.enums.FlinkJobType;
import org.apache.paimon.web.common.util.JSONUtils;

import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Optional;

/** MysqlSyncTableActionContextFactory. */
public class MysqlSyncTableActionContextFactory implements FlinkCdcActionContextFactory {

    @Override
    public String sourceType() {
        return FlinkCdcDataSourceType.MYSQL.getType();
    }

    @Override
    public String targetType() {
        return FlinkCdcDataSourceType.PAIMON.getType();
    }

    @Override
    public FlinkCdcSyncType cdcType() {
        return FlinkCdcSyncType.SINGLE_TABLE_SYNC;
    }

    @Override
    public ActionContext getActionContext(ObjectNode actionConfigs) {
        return MysqlSyncTableActionContext.builder()
                .sessionUrl(
                        Optional.of(String.valueOf(actionConfigs.get(FlinkCdcOptions.SESSION_URL))))
                .flinkJobType(Optional.of(FlinkJobType.SESSION))
                .warehouse(JSONUtils.getString(actionConfigs, FlinkCdcOptions.WAREHOUSE))
                .database(JSONUtils.getString(actionConfigs, FlinkCdcOptions.DATABASE))
                .table(JSONUtils.getString(actionConfigs, FlinkCdcOptions.TABLE))
                .primaryKeys(JSONUtils.getString(actionConfigs, FlinkCdcOptions.PRIMARY_KEYS))
                .actionPath(ActionContextUtil.getActionJarPath())
                .catalogConfList(JSONUtils.getList(actionConfigs, FlinkCdcOptions.CATALOG_CONF))
                .mysqlConfList(JSONUtils.getList(actionConfigs, FlinkCdcOptions.MYSQL_CONF))
                .computedColumnList(
                        JSONUtils.getList(actionConfigs, FlinkCdcOptions.COMPUTED_COLUMN))
                .metaDataColumn(JSONUtils.getString(actionConfigs, FlinkCdcOptions.METADATA_COLUMN))
                .partitionKeys(JSONUtils.getString(actionConfigs, FlinkCdcOptions.PARTITION_KEYS))
                .tableConfList(JSONUtils.getList(actionConfigs, FlinkCdcOptions.TABLE_CONF))
                .executionCheckPointInterval(
                        Optional.of(
                                JSONUtils.getInteger(
                                        actionConfigs, FlinkCdcOptions.EXE_CP_INTERVAL)))
                .pipelineName(
                        Optional.of(
                                JSONUtils.getString(actionConfigs, FlinkCdcOptions.PIPELINE_NAME)))
                .build();
    }
}
