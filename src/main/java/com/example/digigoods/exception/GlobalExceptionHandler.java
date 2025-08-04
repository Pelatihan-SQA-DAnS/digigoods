package com.example.digigoods.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler for the application.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Handles CouponNotFoundException.
   *
   * @param ex the exception
   * @return error response
   */
  @ExceptionHandler(CouponNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleCouponNotFoundException(CouponNotFoundException ex) {
    ErrorResponse errorResponse = new ErrorResponse("COUPON_NOT_FOUND", ex.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }

  /**
   * Handles validation errors.
   *
   * @param ex the exception
   * @return error response
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationException(
      MethodArgumentNotValidException ex) {
    String message = ex.getBindingResult().getFieldErrors().stream()
        .map(error -> error.getField() + ": " + error.getDefaultMessage())
        .findFirst()
        .orElse("Validation failed");

    ErrorResponse errorResponse = new ErrorResponse("VALIDATION_ERROR", message);
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  /**
   * Error response DTO.
   */
  public static class ErrorResponse {
    private String error;
    private String message;

    public ErrorResponse(String error, String message) {
      this.error = error;
      this.message = message;
    }

    public String getError() {
      return error;
    }

    public void setError(String error) {
      this.error = error;
    }

    public String getMessage() {
      return message;
    }

    public void setMessage(String message) {
      this.message = message;
    }
  }
}
