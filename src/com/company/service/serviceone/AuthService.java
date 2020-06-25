package com.company.service.serviceone;

public class AuthService {
    public static final String VALID_TOKEN = "token";
    public static final String NEW_VALID_TOKEN = "new_token";

    public String getAccessToken() {
        return VALID_TOKEN;
    }

    public String refreshAccessToken() {
        return NEW_VALID_TOKEN;
    }
}
