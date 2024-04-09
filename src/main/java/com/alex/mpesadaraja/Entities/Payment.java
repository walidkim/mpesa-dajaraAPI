package com.alex.mpesadaraja.Entities;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @Generated(value = { "" })
    private int paymentID;
    @Column(name = "eslipNo")
    private String eslipNo;
    @Column(name = "checkoutRequestID")
    private String checkoutRequestID;
    @Column(name = "amount")
    private int amount;
    @Column(name = "status")
    private boolean status;

}
