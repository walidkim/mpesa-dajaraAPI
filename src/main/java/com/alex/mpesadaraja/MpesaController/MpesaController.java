package com.alex.mpesadaraja.MpesaController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alex.mpesadaraja.dtos.sendSTKRequest;
import com.alex.mpesadaraja.service.DarajaApiImpl;

@RestController
@RequestMapping("mpesa")
public class MpesaController {
    private DarajaApiImpl darajaApiImpl=new DarajaApiImpl();

    @GetMapping("/sendstk")
    public ResponseEntity<String> sendSTK(@RequestBody sendSTKRequest request) {
    String phoneNo=request.getPhoneNo();
    int amount=request.getAmount();
    String regex="\\d{9}";
    if(phoneNo!=null && amount!=0 && phoneNo.matches(regex)&& amount<=150000){
        return darajaApiImpl.sendSTK(phoneNo, amount);
    }else{
        System.out.println("Provided phone number and amount is invalid");
        return null;
    }
    
}
}