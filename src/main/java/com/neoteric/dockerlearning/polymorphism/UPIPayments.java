package com.neoteric.dockerlearning.polymorphism;

public interface UPIPayments {

    Payment transfer(String fromMobileNumber,String toMobileNumber,Double amount);
}
