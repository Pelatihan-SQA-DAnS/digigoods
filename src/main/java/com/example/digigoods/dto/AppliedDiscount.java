package com.example.digigoods.dto;

import com.example.digigoods.model.DiscountType;
import java.math.BigDecimal;

/**
 * DTO representing an applied discount.
 */
public class AppliedDiscount {
  private String couponCode;
  private String description;
  private DiscountType discountType;
  private BigDecimal discountValue;
  private BigDecimal discountAmount;

  public AppliedDiscount() {
  }

  private AppliedDiscount(Builder builder) {
    this.couponCode = builder.couponCode;
    this.description = builder.description;
    this.discountType = builder.discountType;
    this.discountValue = builder.discountValue;
    this.discountAmount = builder.discountAmount;
  }

  public static Builder builder() {
    return new Builder();
  }

  public String getCouponCode() {
    return couponCode;
  }

  public void setCouponCode(String couponCode) {
    this.couponCode = couponCode;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public DiscountType getDiscountType() {
    return discountType;
  }

  public void setDiscountType(DiscountType discountType) {
    this.discountType = discountType;
  }

  public BigDecimal getDiscountValue() {
    return discountValue;
  }

  public void setDiscountValue(BigDecimal discountValue) {
    this.discountValue = discountValue;
  }

  public BigDecimal getDiscountAmount() {
    return discountAmount;
  }

  public void setDiscountAmount(BigDecimal discountAmount) {
    this.discountAmount = discountAmount;
  }

  public static class Builder {
    private String couponCode;
    private String description;
    private DiscountType discountType;
    private BigDecimal discountValue;
    private BigDecimal discountAmount;

    public Builder couponCode(String couponCode) {
      this.couponCode = couponCode;
      return this;
    }

    public Builder description(String description) {
      this.description = description;
      return this;
    }

    public Builder discountType(DiscountType discountType) {
      this.discountType = discountType;
      return this;
    }

    public Builder discountValue(BigDecimal discountValue) {
      this.discountValue = discountValue;
      return this;
    }

    public Builder discountAmount(BigDecimal discountAmount) {
      this.discountAmount = discountAmount;
      return this;
    }

    public AppliedDiscount build() {
      return new AppliedDiscount(this);
    }
  }
}
