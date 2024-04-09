package com.alex.mpesadaraja.dtos.callback;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Item {
    @JsonProperty("Name")
    private String name;

    @JsonProperty("Value")
    private Object value;

    // Getters and setters
}