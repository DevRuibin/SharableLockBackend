package com.alibou.gateway.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
public class LogModel implements Serializable {

    @JsonProperty("message")
    private String message;

    @JsonProperty("level")
    private String level;

    @JsonProperty("service")
    private String service;
}