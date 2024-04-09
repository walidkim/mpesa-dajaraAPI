package com.alex.mpesadaraja.MpesaController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alex.mpesadaraja.dtos.sendPushRequest;
import com.alex.mpesadaraja.service.DarajaApiImpl;

@RestController
@RequestMapping("mpesa")
public class MpesaController {
    private DarajaApiImpl darajaApiImpl = new DarajaApiImpl();

    @GetMapping("/sendPush")
    public ResponseEntity<String> sendSTK(@RequestBody sendPushRequest request) {
        int amountdue;
        String phoneNo = request.getPhoneNo().trim();
        String amount = request.getAmount().trim();
        String regex = "^[0-9]{9}";
        String regex2 = "^[0-9]{1,6}$";

        // *************Error checking for missing/blank json object

        if (phoneNo != null && amount != null && !phoneNo.isEmpty() && !amount.isEmpty() &&
                phoneNo.matches(regex) && amount.matches(regex2)) {
            amountdue = Integer.parseInt(amount);
            if (amountdue <= 150000 && amountdue > 0) {
                return darajaApiImpl.sendSTK(phoneNo, amountdue);
            } else {
                return ResponseEntity.badRequest()
                        .body("Invalid Phone Number or Amount,Amount should be less than KES150,000");
            }

        } else {
            return ResponseEntity.badRequest()
                    .body("Provide valid phone number and amount.");
        }

    }
}