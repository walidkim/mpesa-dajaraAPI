package com.alex.mpesadaraja.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.alex.mpesadaraja.config;
import com.alex.mpesadaraja.dtos.AccessTokenResponse;
import com.alex.mpesadaraja.dtos.failedSTKResponse;
import com.alex.mpesadaraja.dtos.sendSTKResponse;
import com.alex.mpesadaraja.utils.HelperUtility;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Slf4j
@Service
public class DarajaApiImpl implements DarajaApi {
    private final OkHttpClient okHttpClient = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @SuppressWarnings("resource")
    // Get Access Token
    public String getAccessToken() {
        final String authheader = "Basic " + HelperUtility.toBase64String(config.keySecret);

        Request request = new Request.Builder()
                .url(config.authURL)
                .get()
                .addHeader("Authorization", authheader)
                .addHeader("Content-Type", "application/json")
                .build();

        try {
            Response response = okHttpClient.newCall(request).execute();
            assert response.body() != null;
            AccessTokenResponse accessTokenResponse = objectMapper.readValue(response.body().string(),
                    AccessTokenResponse.class);
            return accessTokenResponse.getAccessToken();
        } catch (Exception e) {
            log.error(String.format("Could not get access token. -> %s", e.getLocalizedMessage()));
            System.out.println("Could not get access token: " + e);
            return null;
        }
    }

    // STK Request

    public ResponseEntity<String> sendSTK(String phoneNo, int amount) {
        phoneNo = "254" + phoneNo;
        try {
            final String authheader = "\"Bearer " + getAccessToken() + "\"";
            assert phoneNo != null && amount > 0 : "Enter proper phone number and amount!!";
            String password = HelperUtility
                    .toBase64String(config.shortCode + config.passkey + HelperUtility.getTimeStamp());
            System.out.println(password);

            StringBuilder stkbody = new StringBuilder();
            stkbody.append("{\"BusinessShortCode\":" + config.shortCode + ",");
            stkbody.append("\"Password\": \"" + password + "\",");
            stkbody.append("\"Timesamp\": \"" + HelperUtility.getTimeStamp() + "\",");
            stkbody.append("\"TransactionType\": \"CustomerPayBillOnline\",");
            stkbody.append("\"Amount\":" + amount + ",");
            stkbody.append("\"PartyA\": " + phoneNo + ",");
            stkbody.append("\"PartyB\":" + config.shortCode + ",");
            stkbody.append("\"PhoneNumber\": " + phoneNo + ",");
            stkbody.append("\"CallBackURL\": \"" + config.callBackURL + "\",");
            stkbody.append("\"AccountReference\": \"Kenya Revenue Authority\",");
            stkbody.append("\"TransactionDesc\": \"Payment for Daraja Test\"}");

            OkHttpClient client = new OkHttpClient().newBuilder().build();
            RequestBody requestBody = RequestBody.create(stkbody.toString(), MediaType.parse("application/json"));
        

            Request request = new Request.Builder()
                    .url(config.stkPushURL)
                    .post(requestBody)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", authheader)
                    .build();

            Response response = client.newCall(request).execute();
            String responseBodyString = response.body().string();

                ObjectMapper objectMapper = new ObjectMapper();     
                 sendSTKResponse SuccessResponse= objectMapper.readValue(responseBodyString, sendSTKResponse.class);
            failedSTKResponse failedResponse=objectMapper.readValue(responseBodyString, failedSTKResponse.class);
            if( SuccessResponse.getCheckoutRequestID()!=null) {

                System.out.println("S Responsecode:"+SuccessResponse.getCheckoutRequestID());
                return ResponseEntity.status(HttpStatus.OK).body( responseBodyString);
            }else{
                
                return ResponseEntity.status(HttpStatus.OK).body(responseBodyString);
            }
        } catch (Exception e) {
            System.out.println("Error occured" + e);
            return ResponseEntity.status(HttpStatus.OK).body("Error occured");
        }
    }

}