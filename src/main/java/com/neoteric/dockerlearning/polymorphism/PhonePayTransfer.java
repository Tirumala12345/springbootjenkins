package com.neoteric.dockerlearning.polymorphism;

import java.util.*;
import java.util.function.BiPredicate;

public class PhonePayTransfer implements UPIPayments{
   public static Map<String,Double> accountBalanceMap=new HashMap<>();
    public static Map<String, ArrayList<Payment>> accountHistoryMap=new HashMap<>();

    static {
        accountBalanceMap.put("9849527319",15000.0);
        accountBalanceMap.put("9052637319",5000.0);
    }

    BiPredicate<String,Double> balanceCheck=(accountNumber,amt) ->{
        Double accountBalance=accountBalanceMap.get(accountNumber);
        if(accountBalance>amt){
            return true;
        }else {
            return false;
        }
    };

    BiPredicate<String,Double> dailyLimitCheck=(accountNumber,amt) ->{
        Double dailyLimit=10000.0;
        ArrayList<Payment> paymentHistoryList=accountHistoryMap.get(accountNumber);
        if(paymentHistoryList!=null){
            Double totalTranscationAmount=0.0;
            for(int i=0;i<paymentHistoryList.size();i++){
               Payment pay= paymentHistoryList.get(i);
               totalTranscationAmount=totalTranscationAmount+pay.getAmount();
            }
            if(totalTranscationAmount<dailyLimit){
                return true;
            }else {
                return false;
            }
        }else {
            return true;
        }
    };

    @Override
    public Payment transfer(String fromMobileNumber, String toMobileNumber, Double amount) {
        Payment payment=new Payment();

        if(balanceCheck.test(fromMobileNumber,amount)) {
            if (dailyLimitCheck.test(fromMobileNumber, amount)) {
                Double fromAccountBalance = accountBalanceMap.get(fromMobileNumber);
                Double toAccountBalance = accountBalanceMap.get(toMobileNumber);

                Double fromTotalBalance = fromAccountBalance - amount;
                Double toTotalBalance = toAccountBalance + amount;

                accountBalanceMap.put(fromMobileNumber, fromTotalBalance);
                accountBalanceMap.put(toMobileNumber, toTotalBalance);

                payment.setStatus(PaymentStatus.SUCCESS.getMessage());
                payment.setTransactionId(UUID.randomUUID().toString());
                payment.setTransactionDate(new Date());
                payment.setUtr(UUID.randomUUID().toString());

                payment.setAmount(amount);

                ArrayList<Payment> paymentArrayList = accountHistoryMap.get(fromMobileNumber);
                if (paymentArrayList != null) {
                    paymentArrayList.add(payment);
                } else {
                    paymentArrayList = new ArrayList<Payment>();
                    paymentArrayList.add(payment);
                    accountHistoryMap.put(fromMobileNumber, paymentArrayList);
                }
            } else {

                payment.setStatus(PaymentStatus.FAILED_DAILY_LIMIT.getMessage());
                payment.setTransactionId(UUID.randomUUID().toString());
                payment.setTransactionDate(new Date());
                payment.setUtr(UUID.randomUUID().toString());
            }

        }else{
            payment.setStatus(PaymentStatus.FAILED.getMessage());
            payment.setTransactionId(UUID.randomUUID().toString());
            payment.setTransactionDate(new Date());
            payment.setUtr(UUID.randomUUID().toString());
        }
        return payment;
    }
}