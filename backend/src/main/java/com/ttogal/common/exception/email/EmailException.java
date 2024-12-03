package com.ttogal.common.exception.email;

import com.ttogal.common.exception.ExceptionCode;
import com.ttogal.common.exception.TtogalException;

public class EmailException extends TtogalException {
  public EmailException(ExceptionCode exceptionCode) {
    super(exceptionCode);
  }
}
