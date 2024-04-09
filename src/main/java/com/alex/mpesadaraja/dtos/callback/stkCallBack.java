package com.alex.mpesadaraja.dtos.callback;

import com.fasterxml.jackson.annotation.JsonProperty;

public class stkCallBack {
    @JsonProperty("MerchantRequestID")
    private String merchantRequestId;

    @JsonProperty("CheckoutRequestID")
    private String checkoutRequestId;

    @JsonProperty("ResultCode")
    private int resultCode;

    @JsonProperty("ResultDesc")
    private String resultDesc;

    @JsonProperty("CallbackMetadata")
    private CallbackMetadata callbackMetadata;

    // Getters and setters
}