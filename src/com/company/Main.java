package com.company;

import com.company.service.serviceone.ServiceFacade;

public class Main {

    public static void main(String[] args) {
        String value1 = ServiceFacade.getInstance().subServiceOne().getValue();
        System.out.println("=== " + value1 + "===");

        String value2 = ServiceFacade.getInstance().subServiceTwo().getValue();
        System.out.println("=== " + value2 + "===");

        // here, we feign token expiry
        // getSecondValue() expects a different token

        String value3 = ServiceFacade.getInstance().subServiceOne().getSecondValue();
        System.out.println("=== " + value3 + "===");

        String value4 = ServiceFacade.getInstance().subServiceTwo().getSecondValue();
        System.out.println("=== " + value4 + "===");
    }
}
