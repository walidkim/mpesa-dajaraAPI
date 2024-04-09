package com.alex.mpesadaraja.dtos;

import lombok.Data;

@Data
public class sendPushRequest {

    private String phoneNo;
    private String amount;

    public sendPushRequest(String phoneNo, String amount) {
        this.phoneNo = phoneNo;
        this.amount = amount;
    }

}
