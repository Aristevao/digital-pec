package com.fho.digitalpec.exception;

import java.io.Serial;

import lombok.Getter;

@Getter
public class UserNotAuthenticatedException extends ApplicationException {

    @Serial
    private static final long serialVersionUID = 631344655102302244L;

    public UserNotAuthenticatedException(String message) {
        super(ErrorCode.USER_NOT_AUTHENTICATED, message);
    }
}
