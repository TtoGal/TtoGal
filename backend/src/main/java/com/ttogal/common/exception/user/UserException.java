package com.ttogal.common.exception.user;

import com.ttogal.common.exception.ExceptionCode;
import com.ttogal.common.exception.TtogalException;

public class UserException extends TtogalException{
  public UserException(ExceptionCode exceptionCode) {
    super(exceptionCode);
  }
}
