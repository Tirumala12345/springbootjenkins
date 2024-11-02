package com.neoteric.dockerlearning.polymorphism;

public enum PaymentStatus {

    SUCCESS("Transaction Successful"),
    PENDING("Transaction Is Under Progress"),
    FAILED_DAILY_LIMIT("Exceeded Daily Limit"),
    FAILED("Transaction Failed");

    private String  message;

    PaymentStatus(String message){
        this.message=message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
