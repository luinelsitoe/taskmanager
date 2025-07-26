package com.luinel.taskmanager.error;

public class InvalidNameException extends RuntimeException {
  public InvalidNameException(String message) {
    super(message);
  }
}
