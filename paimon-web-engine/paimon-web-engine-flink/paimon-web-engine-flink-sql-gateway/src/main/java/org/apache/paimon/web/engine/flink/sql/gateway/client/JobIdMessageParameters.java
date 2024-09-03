package org.apache.paimon.web.engine.flink.sql.gateway.client;

import org.apache.flink.runtime.rest.messages.MessageParameters;
import org.apache.flink.runtime.rest.messages.MessagePathParameter;
import org.apache.flink.runtime.rest.messages.MessageQueryParameter;

import java.util.Collection;
import java.util.Collections;

public class JobIdMessageParameters extends MessageParameters {

    private final JobIdMessagePathParameter jobIdMessagePathParameter = new JobIdMessagePathParameter();

    public JobIdMessageParameters(String jobId) {
        jobIdMessagePathParameter.resolve(jobId);
    }

    @Override
    public Collection<MessagePathParameter<?>> getPathParameters() {
        return Collections.singletonList(this.jobIdMessagePathParameter);
    }

    @Override
    public Collection<MessageQueryParameter<?>> getQueryParameters() {
        return Collections.emptyList();
    }

}
