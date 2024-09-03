package org.apache.paimon.web.engine.flink.sql.gateway.model;

import lombok.Getter;
import org.apache.flink.runtime.messages.webmonitor.InfoMessage;
import org.apache.flink.runtime.rest.messages.ResponseBody;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.annotation.JsonCreator;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.annotation.JsonProperty;

@Getter
public class TriggerIdEntity implements ResponseBody, InfoMessage {

    public static final String FIELD_NAME_TRIGGER_ID = "request-id";

    @JsonProperty(FIELD_NAME_TRIGGER_ID)
    private String triggerId;

    @JsonCreator
    public TriggerIdEntity(@JsonProperty(FIELD_NAME_TRIGGER_ID)
                             String triggerId){
        this.triggerId = triggerId;
    }

}
