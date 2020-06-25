package com.company.service.serviceone;

import com.company.service.util.AuthException;

import static com.company.service.serviceone.AuthService.NEW_VALID_TOKEN;
import static com.company.service.serviceone.AuthService.VALID_TOKEN;

public class SubServiceTwo extends ASubService implements ISubServiceTwo {

    public SubServiceTwo() {
        this(null);
    }

    public SubServiceTwo(String token) {
        System.out.println("creating a new instance");
        this.token = token;
    }

    public String getValue() throws AuthException {
        if (VALID_TOKEN.equals(this.token)) {
            return "min first value";
        }

        throw new AuthException();
    }

    public String getSecondValue() throws AuthException {
        if (NEW_VALID_TOKEN.equals(this.token)) {
            return "min second value";
        }

        throw new AuthException();
    }
}
