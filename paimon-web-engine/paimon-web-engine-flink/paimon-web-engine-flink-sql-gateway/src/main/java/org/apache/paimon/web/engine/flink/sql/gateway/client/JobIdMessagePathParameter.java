package org.apache.paimon.web.engine.flink.sql.gateway.client;

import org.apache.flink.runtime.rest.messages.ConversionException;
import org.apache.flink.runtime.rest.messages.MessagePathParameter;

public class JobIdMessagePathParameter extends MessagePathParameter<String> {

    public static final String KEY = "jobid";

    protected JobIdMessagePathParameter() {
        super(KEY);
    }

    @Override
    protected String convertFromString(String jobid) throws ConversionException {
        return jobid;
    }

    @Override
    protected String convertToString(String jobid) {
        return jobid;
    }

    @Override
    public String getDescription() {
        return "the id of flink job";
    }
}
