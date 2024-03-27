package com.alex.mpesadaraja.MpesaController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alex.mpesadaraja.dtos.sendSTKRequest;
import com.alex.mpesadaraja.dtos.sendSTKResponse;
import com.alex.mpesadaraja.service.DarajaApiImpl;

@RestController
@RequestMapping("mpesa")
public class MpesaController {
    private DarajaApiImpl darajaApiImpl=new DarajaApiImpl();

    @GetMapping("/sendstk")
    public sendSTKResponse sendSTK(@RequestBody sendSTKRequest request) {
    String phoneNo=request.getPhoneNo();
    int amount=request.getAmount();
        return darajaApiImpl.sendSTK(phoneNo, amount);
    }
    
}
