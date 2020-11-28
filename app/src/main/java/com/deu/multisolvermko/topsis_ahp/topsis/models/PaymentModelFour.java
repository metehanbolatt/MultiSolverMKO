package com.deu.multisolvermko.topsis_ahp.topsis.models;

public class PaymentModelFour {

    String id;
    String name;
    String payment;
    String payment1;

    public PaymentModelFour(String id, String name, String payment, String payment1) {
        this.id = id;
        this.name = name;
        this.payment = payment;
        this.payment1 = payment1;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPayment() {
        return payment;
    }

    public String getPayment1() {
        return payment1;
    }
}
