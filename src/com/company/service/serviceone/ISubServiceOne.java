package com.company.service.serviceone;

import com.company.service.util.AuthException;

public interface ISubServiceOne {
    String getValue() throws AuthException;
    String getSecondValue() throws AuthException;
}
