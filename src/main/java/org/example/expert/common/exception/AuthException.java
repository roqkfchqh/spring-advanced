package org.example.expert.common.exception;

public class AuthException extends BaseException {

    public AuthException(ErrorCode errorCode) {
        super(errorCode.getMessage(), errorCode.getStatus());
    }
}
