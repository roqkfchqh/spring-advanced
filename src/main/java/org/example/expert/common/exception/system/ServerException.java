package org.example.expert.common.exception.system;

import org.example.expert.common.exception.base.BaseException;
import org.example.expert.common.exception.base.ErrorCode;

public class ServerException extends BaseException {

    public ServerException(ErrorCode errorCode) {
        super(errorCode.getMessage(), errorCode.getStatus());
    }
}
