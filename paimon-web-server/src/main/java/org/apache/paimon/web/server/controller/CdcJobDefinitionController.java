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

package org.apache.paimon.web.server.controller;

import org.apache.paimon.web.engine.flink.common.status.JobStatus;
import org.apache.paimon.web.server.data.dto.CdcJobDefinitionDTO;
import org.apache.paimon.web.server.data.dto.CdcJobSubmitDTO;
import org.apache.paimon.web.server.data.model.CdcJobDefinition;
import org.apache.paimon.web.server.data.result.PageR;
import org.apache.paimon.web.server.data.result.R;
import org.apache.paimon.web.server.data.vo.ActionExecutionResultVo;
import org.apache.paimon.web.server.data.vo.CdcJobDefinitionVO;
import org.apache.paimon.web.server.service.CdcJobDefinitionService;

import cn.dev33.satoken.annotation.SaCheckPermission;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/** CdcJobDefinition api controller. */
@Slf4j
@Validated
@RestController
@RequestMapping("/api/cdc-job-definition")
public class CdcJobDefinitionController {

    private CdcJobDefinitionService cdcJobDefinitionService;

    public CdcJobDefinitionController(CdcJobDefinitionService cdcJobDefinitionService) {
        this.cdcJobDefinitionService = cdcJobDefinitionService;
    }

    @SaCheckPermission("cdc:job:create")
    @PostMapping("create")
    public R<Void> createCdcJob(@Valid @RequestBody CdcJobDefinitionDTO cdcJobDefinitionDTO) {
        return cdcJobDefinitionService.create(cdcJobDefinitionDTO);
    }

    @SaCheckPermission("cdc:job:update")
    @PutMapping("update")
    public R<Void> updateCdcJob(@Valid @RequestBody CdcJobDefinitionDTO cdcJobDefinitionDTO) {
        return cdcJobDefinitionService.update(cdcJobDefinitionDTO);
    }

    @SaCheckPermission("cdc:job:list")
    @GetMapping("list")
    public PageR<CdcJobDefinitionVO> listAllCdcJob(
            @RequestParam(required = false) boolean withConfig,
            @RequestParam(required = false) String jobName,
            @RequestParam long currentPage,
            @RequestParam long pageSize) {
        return cdcJobDefinitionService.listAll(jobName, withConfig, currentPage, pageSize);
    }

    @SaCheckPermission("cdc:job:query")
    @GetMapping("/{id}")
    public R<CdcJobDefinition> getById(@PathVariable Integer id) {
        CdcJobDefinition cdcJobDefinition = cdcJobDefinitionService.getById(id);
        if (cdcJobDefinition == null) {
            return R.failed();
        }
        return R.succeed(cdcJobDefinition);
    }

    @SaCheckPermission("cdc:job:delete")
    @DeleteMapping("{id}")
    public R<Void> deleteById(@PathVariable Integer id) {
        return cdcJobDefinitionService.deleteById(id);
    }

    @SaCheckPermission("cdc:job:copy")
    @PutMapping("{id}/copy")
    public R<Integer> copy(@PathVariable Integer id) {
        return cdcJobDefinitionService.copy(id);
    }

    @SaCheckPermission("cdc:job:submit")
    @PostMapping("{id}/submit")
    public R<ActionExecutionResultVo> submit(
            @PathVariable Integer id, @RequestBody CdcJobSubmitDTO cdcJobSubmitDTO) {
        return cdcJobDefinitionService.submit(id, cdcJobSubmitDTO);
    }

    @SaCheckPermission("cdc:job:cancel")
    @PostMapping("{id}/cancel")
    public R<ActionExecutionResultVo> cancel(@PathVariable Integer id) {
        return cdcJobDefinitionService.cancel(id);
    }

    @SaCheckPermission("cdc:job:status")
    @GetMapping("{id}/status")
    public R<JobStatus> status(@PathVariable Integer id, @RequestParam Integer logId) {
        return cdcJobDefinitionService.status(id, logId);
    }
}
