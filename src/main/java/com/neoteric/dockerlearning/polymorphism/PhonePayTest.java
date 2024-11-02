package com.neoteric.dockerlearning.polymorphism;

public class PhonePayTest {
    public static void main(String[] args) {

        UPIPayments phonePayTransfer=new PhonePayTransfer();
        Payment payment=phonePayTransfer.transfer("9849527319","9052637319",5000.0);

        Payment payment2=phonePayTransfer.transfer("9849527319","9052637319",5000.0);

        Payment payment3=phonePayTransfer.transfer("9849527319","9052637319",2000.0);



        System.out.println("Transcation Id" +payment.getTransactionId() +"Transcation 1 " +payment.getStatus());
        System.out.println("Transcation Id" +payment2.getTransactionId() +"Transcation 2 " +payment2.getStatus());
        System.out.println("Transcation Id" +payment3.getTransactionId() +"Transcation 3 " +payment3.getStatus());

        System.out.println("from account balance" + PhonePayTransfer.accountBalanceMap.get("9849527319"));
        System.out.println("to account balance" + PhonePayTransfer.accountBalanceMap.get("9052637319"));
    }
}