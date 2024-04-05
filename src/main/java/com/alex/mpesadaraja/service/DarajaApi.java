package com.alex.mpesadaraja.service;

import org.springframework.http.ResponseEntity;

public interface DarajaApi {

String getAccessToken();
//STKRequestResponse sendSTK(String phoneNo, int amount);
ResponseEntity<String> sendSTK(String phoneNo, int amount);

}