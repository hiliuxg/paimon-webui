package org.apache.paimon.web.engine.flink.sql.gateway.client;

import org.apache.flink.runtime.rest.HttpMethodWrapper;
import org.apache.flink.runtime.rest.messages.EmptyRequestBody;
import org.apache.flink.runtime.rest.messages.RuntimeMessageHeaders;
import org.apache.flink.shaded.netty4.io.netty.handler.codec.http.HttpResponseStatus;
import org.apache.paimon.web.engine.flink.sql.gateway.model.JobOverviewEntity;


public class JobOverViewHeaders implements RuntimeMessageHeaders<EmptyRequestBody, JobOverviewEntity, JobIdMessageParameters> {

    private final String jobId;
    public static final String URL = "/jobs/:" + JobIdMessagePathParameter.KEY;

    public JobOverViewHeaders(String jobId) {
        this.jobId = jobId;
    }

    @Override
    public Class<JobOverviewEntity> getResponseClass() {
        return JobOverviewEntity.class;
    }

    @Override
    public HttpResponseStatus getResponseStatusCode() {
        return HttpResponseStatus.OK;
    }

    @Override
    public String getDescription() {
        return "Returns details of a job.";
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
        return HttpMethodWrapper.GET;
    }

    @Override
    public String getTargetRestEndpointURL() {
        return URL;
    }

}
