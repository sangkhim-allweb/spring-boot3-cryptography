package com.sangkhim.spring_boot3_cryptography.exception;

import com.sangkhim.spring_boot3_cryptography.exception.base.ServiceException;

/** trigger for duplicate exception */
public class DuplicateException extends ServiceException {

  public DuplicateException() {
    super();
  }

  public DuplicateException(String message) {
    super(message);
  }

  public DuplicateException(String message, Throwable throwable) {
    super(message, throwable);
  }
}
