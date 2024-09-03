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

package org.apache.paimon.web.server.service.impl;

import org.apache.paimon.web.server.data.model.CdcJobLog;
import org.apache.paimon.web.server.mapper.CdcJobLogMapper;
import org.apache.paimon.web.server.service.CdcJobLogService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/** CdcJobLogImpl. */
@Service
@Slf4j
public class CdcJobLogImpl extends ServiceImpl<CdcJobLogMapper, CdcJobLog>
        implements CdcJobLogService {

    @Override
    public CdcJobLog findLast(Integer cdcJobDefinitionId) {
        QueryWrapper<CdcJobLog> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("cdc_job_definition_id", cdcJobDefinitionId)
                .orderByDesc("create_time")
                .last("LIMIT 1");
        return baseMapper.selectOne(queryWrapper);
    }
}
