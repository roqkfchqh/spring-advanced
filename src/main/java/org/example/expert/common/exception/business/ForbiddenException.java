package org.example.expert.common.exception.business;

import org.example.expert.common.exception.base.BaseException;
import org.example.expert.common.exception.base.ErrorCode;

public class ForbiddenException extends BaseException {
    public ForbiddenException(ErrorCode errorCode) {
        super(errorCode.getMessage(), errorCode.getStatus());
    }
}
