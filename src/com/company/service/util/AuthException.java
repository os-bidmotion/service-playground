package com.company.service.util;

public class AuthException extends RuntimeException {
    public AuthException() {
        super("401");
    }
}
