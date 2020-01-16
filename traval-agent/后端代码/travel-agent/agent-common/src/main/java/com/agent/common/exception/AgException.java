package com.agent.common.exception;

import com.agent.common.enums.ExceptionEnum;
import lombok.Data;

@Data
public class AgException extends RuntimeException {
    private ExceptionEnum exceptionEnum;

    public AgException(ExceptionEnum exceptionEnum) {
        this.exceptionEnum = exceptionEnum;
    }
}
