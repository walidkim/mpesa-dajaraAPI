package com.alex.mpesadaraja.service;

import com.alex.mpesadaraja.dtos.sendSTKResponse;

public interface DarajaApi {

String getAccessToken();
//STKRequestResponse sendSTK(String phoneNo, int amount);
sendSTKResponse sendSTK(String phoneNo, int amount);

}