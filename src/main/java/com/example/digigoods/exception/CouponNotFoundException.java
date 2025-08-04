package com.example.digigoods.exception;

/**
 * Exception thrown when a coupon is not found.
 */
public class CouponNotFoundException extends RuntimeException {

  public CouponNotFoundException(String message) {
    super(message);
  }
}
