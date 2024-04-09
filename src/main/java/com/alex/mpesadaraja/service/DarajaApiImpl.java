package com.alex.mpesadaraja.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.alex.mpesadaraja.config;
import com.alex.mpesadaraja.dtos.AccessTokenResponse;
import com.alex.mpesadaraja.dtos.errorPushResponse;
import com.alex.mpesadaraja.dtos.okPushResponse;
import com.alex.mpesadaraja.dtos.callback.StkCallbackRequest;
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
            final String authheader = "Bearer " + getAccessToken();
            assert phoneNo != null && amount > 0 : "Enter proper phone number and amount!!";
            String password = HelperUtility
                    .toBase64String(config.shortCode + config.passkey + HelperUtility.getTimeStamp());

            String stkBody = "{\"BusinessShortCode\":" + config.shortCode + ",";
            stkBody += "\"Password\": \"" + password + "\",";
            stkBody += "\"Timestamp\": \"" + HelperUtility.getTimeStamp() + "\",";
            stkBody += "\"TransactionType\": \"CustomerPayBillOnline\",";
            stkBody += "\"Amount\":" + amount + ",";
            stkBody += "\"PartyA\": " + phoneNo + ",";
            stkBody += "\"PartyB\":" + config.shortCode + ",";
            stkBody += "\"PhoneNumber\": " + phoneNo + ",";
            stkBody += "\"CallBackURL\": \"" + config.callBackURL + "\",";
            stkBody += "\"AccountReference\": \"CompanyXLTD\",";
            stkBody += "\"TransactionDesc\": \"Payment for Daraja Test\"}";

            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = RequestBody.create(stkBody, MediaType.parse("application/json"));

            Request request = new Request.Builder()
                    .url(config.stkPushURL)
                    .post(requestBody)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", authheader)
                    .build();

            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();

            if (response.isSuccessful()) {
                okPushResponse okPushResponse = objectMapper.readValue(responseBody, okPushResponse.class);
                System.out.println(okPushResponse.getResponseCode());
                if ("0".equals(okPushResponse.getResponseCode())) {
                    return ResponseEntity.ok().body("STK Push successful:  " + okPushResponse.toString());
                } else {
                    return ResponseEntity.ok().body("STK Push failed: " + okPushResponse.getResponseDescription());
                }
            } else {
                errorPushResponse errorPushResponse = objectMapper.readValue(responseBody, errorPushResponse.class);
                return ResponseEntity.ok().body("STK Push failed:   " + errorPushResponse.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred");
        }
    }

    public void CallBack throws Exception
    {
        // Sample JSON response
        String jsonResponse = "{ \"Body\": { \"stkCallback\": { \"MerchantRequestID\": \"29115-34620561-1\", \"CheckoutRequestID\": \"ws_CO_191220191020363925\", \"ResultCode\": 0, \"ResultDesc\": \"The service request is processed successfully.\", \"CallbackMetadata\": { \"Item\": [ { \"Name\": \"Amount\", \"Value\": 1.00 }, { \"Name\": \"MpesaReceiptNumber\", \"Value\": \"NLJ7RT61SV\" }, { \"Name\": \"TransactionDate\", \"Value\": 20191219102115 }, { \"Name\": \"PhoneNumber\", \"Value\": 254708374149 } ] } } } }";

        // Create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();

        // Deserialize JSON response into StkCallbackRequest object
        StkCallbackRequest stkCallbackRequest = objectMapper.readValue(jsonResponse, StkCallbackRequest.class);

        // Now you can access the parsed data
        StkCallback stkCallback = stkCallbackRequest.getBody().getStkCallback();
        System.out.println("Merchant Request ID: " + stkCallback.getMerchantRequestId());
        System.out.println("Checkout Request ID: " + stkCallback.getCheckoutRequestId());
        // Similarly, access other fields as needed
    }
}
