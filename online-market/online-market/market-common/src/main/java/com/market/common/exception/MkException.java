package com.market.common.exception;

import com.market.common.enums.ExceptionEnum;
import lombok.Data;

@Data
public class MkException extends RuntimeException {
    private ExceptionEnum exceptionEnum;

    public MkException(ExceptionEnum exceptionEnum) {
        this.exceptionEnum = exceptionEnum;
    }
}
