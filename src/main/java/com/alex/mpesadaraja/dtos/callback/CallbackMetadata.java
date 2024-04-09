package com.alex.mpesadaraja.dtos.callback;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CallbackMetadata {
    @JsonProperty("Item")
    private List<Item> items;

    // Getters and setters
}