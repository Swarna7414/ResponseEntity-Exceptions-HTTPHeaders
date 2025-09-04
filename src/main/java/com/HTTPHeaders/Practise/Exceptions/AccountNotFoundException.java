package com.HTTPHeaders.Practise.Exceptions;

public class AccountNotFoundException extends RuntimeException {
  public AccountNotFoundException(Long id) {
    super("Account Id was not found"+id);
  }
}