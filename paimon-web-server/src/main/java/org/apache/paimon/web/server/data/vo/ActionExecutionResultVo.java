package org.apache.paimon.web.server.data.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.paimon.web.engine.flink.common.status.JobStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActionExecutionResultVo {

    private Integer logId;

    private boolean success;

    private String errorMsg;

    private JobStatus jobStatus;

}
