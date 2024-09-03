package org.apache.paimon.web.engine.flink.sql.gateway.client;

import org.apache.flink.runtime.rest.HttpMethodWrapper;
import org.apache.flink.runtime.rest.messages.EmptyRequestBody;
import org.apache.flink.runtime.rest.messages.RuntimeMessageHeaders;
import org.apache.flink.shaded.netty4.io.netty.handler.codec.http.HttpResponseStatus;
import org.apache.paimon.web.engine.flink.sql.gateway.model.TriggerIdEntity;


public class StopWithSavePointHeaders implements RuntimeMessageHeaders<EmptyRequestBody, TriggerIdEntity, JobIdMessageParameters> {

    private final String jobId;
    public static final String URL = "/jobs/:" + JobIdMessagePathParameter.KEY + "/stop";

    public StopWithSavePointHeaders(String jobId) {
        this.jobId = jobId;
    }

    @Override
    public Class<TriggerIdEntity> getResponseClass() {
        return TriggerIdEntity.class;
    }

    @Override
    public HttpResponseStatus getResponseStatusCode() {
        return HttpResponseStatus.OK;
    }

    @Override
    public String getDescription() {
        return "Stops a job with a savepoint. Optionally, it can also emit a MAX_WATERMARK before taking the savepoint" +
                " to flush out any state waiting for timers to fire. This async operation would return a 'triggerid' " +
                "for further query identifier.";
    }

    @Override
    public Class<EmptyRequestBody> getRequestClass() {
        return EmptyRequestBody.class;
    }

    @Override
    public JobIdMessageParameters getUnresolvedMessageParameters() {
        return new JobIdMessageParameters(jobId);
    }

    @Override
    public HttpMethodWrapper getHttpMethod() {
        return HttpMethodWrapper.POST;
    }

    @Override
    public String getTargetRestEndpointURL() {
        return URL;
    }

}
