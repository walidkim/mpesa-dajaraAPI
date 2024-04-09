package com.alex.mpesadaraja.dtos.callback;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StkCallbackRequest {
    @JsonProperty("Body")
    private StkCallbackBody body;

    // Getters and setters
}