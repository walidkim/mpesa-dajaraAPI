package com.alex.mpesadaraja.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class errorPushResponse {
    @JsonProperty("requestId")
    private String requestId;
    @JsonProperty("errorCode")
    private String errorCode;
    @JsonProperty("errorMessage")
    private String errorMessage;
}
