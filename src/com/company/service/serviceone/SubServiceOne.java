package com.company.service.serviceone;

import com.company.service.util.AuthException;

import static com.company.service.serviceone.AuthService.NEW_VALID_TOKEN;
import static com.company.service.serviceone.AuthService.VALID_TOKEN;

public class SubServiceOne extends ASubService implements ISubServiceOne {

    public SubServiceOne() {
        this(null);
    }

    public SubServiceOne(String token) {
        System.out.println("creating a new instance");
        this.token = token;
    }

    public String getValue() throws AuthException {
        if (VALID_TOKEN.equals(this.token)) {
            return "sub first value";
        }

        throw new AuthException();
    }

    public String getSecondValue() throws AuthException {
        if (NEW_VALID_TOKEN.equals(this.token)) {
            return "sub second value";
        }

        throw new AuthException();
    }
}
