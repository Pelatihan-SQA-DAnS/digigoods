package com.example.digigoods.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 * Response DTO for discount calculation.
 */
public class DiscountCalculationResponse {
  private BigDecimal originalTotal;
  private BigDecimal discountAmount;
  private BigDecimal finalTotal;
  private List<AppliedDiscount> appliedDiscounts;

  public DiscountCalculationResponse() {
  }

  private DiscountCalculationResponse(Builder builder) {
    this.originalTotal = builder.originalTotal;
    this.discountAmount = builder.discountAmount;
    this.finalTotal = builder.finalTotal;
    this.appliedDiscounts = builder.appliedDiscounts;
  }

  public static Builder builder() {
    return new Builder();
  }

  public BigDecimal getOriginalTotal() {
    return originalTotal;
  }

  public void setOriginalTotal(BigDecimal originalTotal) {
    this.originalTotal = originalTotal;
  }

  public BigDecimal getDiscountAmount() {
    return discountAmount;
  }

  public void setDiscountAmount(BigDecimal discountAmount) {
    this.discountAmount = discountAmount;
  }

  public BigDecimal getFinalTotal() {
    return finalTotal;
  }

  public void setFinalTotal(BigDecimal finalTotal) {
    this.finalTotal = finalTotal;
  }

  public List<AppliedDiscount> getAppliedDiscounts() {
    return appliedDiscounts;
  }

  public void setAppliedDiscounts(List<AppliedDiscount> appliedDiscounts) {
    this.appliedDiscounts = appliedDiscounts;
  }

  public static class Builder {
    private BigDecimal originalTotal;
    private BigDecimal discountAmount;
    private BigDecimal finalTotal;
    private List<AppliedDiscount> appliedDiscounts;

    public Builder originalTotal(BigDecimal originalTotal) {
      this.originalTotal = originalTotal;
      return this;
    }

    public Builder discountAmount(BigDecimal discountAmount) {
      this.discountAmount = discountAmount;
      return this;
    }

    public Builder finalTotal(BigDecimal finalTotal) {
      this.finalTotal = finalTotal;
      return this;
    }

    public Builder appliedDiscounts(List<AppliedDiscount> appliedDiscounts) {
      this.appliedDiscounts = appliedDiscounts;
      return this;
    }

    public DiscountCalculationResponse build() {
      return new DiscountCalculationResponse(this);
    }
  }
}
