package com.alex.mpesadaraja.service;

import org.springframework.http.ResponseEntity;

public interface DarajaApi {

    String getAccessToken();

    ResponseEntity<String> sendSTK(String phoneNo, int amount);

}