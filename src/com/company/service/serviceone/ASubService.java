package com.company.service.serviceone;

import com.company.service.util.ITokenObserver;

public abstract class ASubService implements ITokenObserver {
    protected String token;

    @Override
    public void updateToken(String token) {
        System.out.println("updating token for " + this.getClass().getSimpleName());
        this.token = token;
    }
}
