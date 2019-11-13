package com.market.common.advice;

import com.market.common.exception.MkException;
import com.market.common.vo.ExceptionResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class BasicExceptionHandler {
    @ExceptionHandler(value = MkException.class)
    public ResponseEntity<ExceptionResult> handlerException(MkException e){
        return ResponseEntity.status(e.getExceptionEnum().getCode()).body(new ExceptionResult(e.getExceptionEnum()));
    }
}
