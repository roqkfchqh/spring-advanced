package org.example.expert.common.exception.business;

import org.example.expert.common.exception.base.BaseException;
import org.example.expert.common.exception.base.ErrorCode;

public class InvalidRequestException extends BaseException {
    public InvalidRequestException(ErrorCode errorCode) {
        super(errorCode.getMessage(), errorCode.getStatus());
    }
}
