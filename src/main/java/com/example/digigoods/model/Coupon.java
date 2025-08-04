package com.example.digigoods.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Domain model representing a discount coupon.
 */
public class Coupon {
  private Long id;
  private String code;
  private String description;
  private DiscountType discountType;
  private BigDecimal discountValue;
  private BigDecimal minimumOrderAmount;
  private LocalDateTime expiryDate;
  private Integer usageLimit;
  private Integer usageCount;
  private Boolean active;

  public Coupon() {
  }

  private Coupon(Builder builder) {
    this.id = builder.id;
    this.code = builder.code;
    this.description = builder.description;
    this.discountType = builder.discountType;
    this.discountValue = builder.discountValue;
    this.minimumOrderAmount = builder.minimumOrderAmount;
    this.expiryDate = builder.expiryDate;
    this.usageLimit = builder.usageLimit;
    this.usageCount = builder.usageCount;
    this.active = builder.active;
  }

  public static Builder builder() {
    return new Builder();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public Integer getUsageCount() {
    return usageCount;
  }

  public void setUsageCount(Integer usageCount) {
    this.usageCount = usageCount;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

  public static class Builder {
    private Long id;
    private String code;
    private String description;
    private DiscountType discountType;
    private BigDecimal discountValue;
    private BigDecimal minimumOrderAmount;
    private LocalDateTime expiryDate;
    private Integer usageLimit;
    private Integer usageCount;
    private Boolean active;

    public Builder id(Long id) {
      this.id = id;
      return this;
    }

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

    public Builder usageCount(Integer usageCount) {
      this.usageCount = usageCount;
      return this;
    }

    public Builder active(Boolean active) {
      this.active = active;
      return this;
    }

    public Coupon build() {
      return new Coupon(this);
    }
  }
}
