package com.ttogal.common.exception;

import lombok.Getter;

@Getter
public abstract class TtogalException extends RuntimeException {
  private final ExceptionCode exceptionCode;

  public TtogalException(ExceptionCode exceptionCode) {
    super(exceptionCode.getMessage());
    this.exceptionCode = exceptionCode;
  }
}
