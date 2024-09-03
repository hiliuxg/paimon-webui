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
