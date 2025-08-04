package com.example.digigoods.dto;

import com.example.digigoods.model.DiscountType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Request DTO for creating a new coupon.
 */
public class CreateCouponRequest {
  @NotBlank(message = "Coupon code is required")
  private String code;

  @NotBlank(message = "Description is required")
  private String description;

  @NotNull(message = "Discount type is required")
  private DiscountType discountType;

  @NotNull(message = "Discount value is required")
  @DecimalMin(value = "0.0", inclusive = false, message = "Discount value must be positive")
  private BigDecimal discountValue;

  @DecimalMin(value = "0.0", message = "Minimum order amount must be non-negative")
  private BigDecimal minimumOrderAmount;

  @Future(message = "Expiry date must be in the future")
  private LocalDateTime expiryDate;

  @Min(value = 1, message = "Usage limit must be at least 1")
  private Integer usageLimit;

  public CreateCouponRequest() {
  }

  private CreateCouponRequest(Builder builder) {
    this.code = builder.code;
    this.description = builder.description;
    this.discountType = builder.discountType;
    this.discountValue = builder.discountValue;
    this.minimumOrderAmount = builder.minimumOrderAmount;
    this.expiryDate = builder.expiryDate;
    this.usageLimit = builder.usageLimit;
  }

  public static Builder builder() {
    return new Builder();
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
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

  public BigDecimal getMinimumOrderAmount() {
    return minimumOrderAmount;
  }

  public void setMinimumOrderAmount(BigDecimal minimumOrderAmount) {
    this.minimumOrderAmount = minimumOrderAmount;
  }

  public LocalDateTime getExpiryDate() {
    return expiryDate;
  }

  public void setExpiryDate(LocalDateTime expiryDate) {
    this.expiryDate = expiryDate;
  }

  public Integer getUsageLimit() {
    return usageLimit;
  }

  public void setUsageLimit(Integer usageLimit) {
    this.usageLimit = usageLimit;
  }

  public static class Builder {
    private String code;
    private String description;
    private DiscountType discountType;
    private BigDecimal discountValue;
    private BigDecimal minimumOrderAmount;
    private LocalDateTime expiryDate;
    private Integer usageLimit;

    public Builder code(String code) {
      this.code = code;
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

    public Builder minimumOrderAmount(BigDecimal minimumOrderAmount) {
      this.minimumOrderAmount = minimumOrderAmount;
      return this;
    }

    public Builder expiryDate(LocalDateTime expiryDate) {
      this.expiryDate = expiryDate;
      return this;
    }

    public Builder usageLimit(Integer usageLimit) {
      this.usageLimit = usageLimit;
      return this;
    }

    public CreateCouponRequest build() {
      return new CreateCouponRequest(this);
    }
  }
}
