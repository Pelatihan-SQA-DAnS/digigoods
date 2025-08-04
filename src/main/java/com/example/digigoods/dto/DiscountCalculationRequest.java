package com.example.digigoods.dto;

import java.util.List;

/**
 * Request DTO for discount calculation.
 */
public class DiscountCalculationRequest {
  private List<CartItem> cartItems;
  private List<String> couponCodes;

  public DiscountCalculationRequest() {
  }

  private DiscountCalculationRequest(Builder builder) {
    this.cartItems = builder.cartItems;
    this.couponCodes = builder.couponCodes;
  }

  public static Builder builder() {
    return new Builder();
  }

  public List<CartItem> getCartItems() {
    return cartItems;
  }

  public void setCartItems(List<CartItem> cartItems) {
    this.cartItems = cartItems;
  }

  public List<String> getCouponCodes() {
    return couponCodes;
  }

  public void setCouponCodes(List<String> couponCodes) {
    this.couponCodes = couponCodes;
  }

  public static class Builder {
    private List<CartItem> cartItems;
    private List<String> couponCodes;

    public Builder cartItems(List<CartItem> cartItems) {
      this.cartItems = cartItems;
      return this;
    }

    public Builder couponCodes(List<String> couponCodes) {
      this.couponCodes = couponCodes;
      return this;
    }

    public DiscountCalculationRequest build() {
      return new DiscountCalculationRequest(this);
    }
  }
}
