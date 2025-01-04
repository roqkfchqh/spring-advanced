package org.example.expert.common.exception.business;

import org.example.expert.common.exception.base.BaseException;
import org.example.expert.common.exception.base.ErrorCode;

public class AuthException extends BaseException {

    public AuthException(ErrorCode errorCode) {
        super(errorCode.getMessage(), errorCode.getStatus());
    }
}
