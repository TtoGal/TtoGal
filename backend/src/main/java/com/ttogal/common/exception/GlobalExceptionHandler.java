package com.ttogal.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(TtogalException.class)
  public ResponseEntity<ExceptionResponse> handlerBadRequestException(TtogalException e) {

    log.warn(e.getMessage(), e);

    return ResponseEntity
            .status(e.getExceptionCode().getCode())
            .body(new ExceptionResponse(e.getExceptionCode().getCode(), e.getMessage()));
  }
}

