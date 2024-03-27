package com.alex.mpesadaraja.dtos;

import lombok.Data;

@Data
public class sendSTKRequest {

    private String phoneNo;
    private int amount;
    public sendSTKRequest(String phoneNo, int amount) {
        this.phoneNo = phoneNo;
        this.amount = amount;
    }

    

}
