package com.deu.multisolvermko.topsis_ahp.topsis.models;

public class PaymentModel {

    String id;
    String name;
    String payment;

    public PaymentModel(String id, String name, String payment) {
        this.id = id;
        this.name = name;
        this.payment = payment;
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
}
