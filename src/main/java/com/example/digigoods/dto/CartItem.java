package com.example.digigoods.dto;

import java.math.BigDecimal;

/**
 * DTO representing an item in the shopping cart.
 */
public class CartItem {
  private String productId;
  private String productName;
  private BigDecimal unitPrice;
  private Integer quantity;

  public CartItem() {
  }

  private CartItem(Builder builder) {
    this.productId = builder.productId;
    this.productName = builder.productName;
    this.unitPrice = builder.unitPrice;
    this.quantity = builder.quantity;
  }

  public static Builder builder() {
    return new Builder();
  }

  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public BigDecimal getUnitPrice() {
    return unitPrice;
  }

  public void setUnitPrice(BigDecimal unitPrice) {
    this.unitPrice = unitPrice;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public static class Builder {
    private String productId;
    private String productName;
    private BigDecimal unitPrice;
    private Integer quantity;

    public Builder productId(String productId) {
      this.productId = productId;
      return this;
    }

    public Builder productName(String productName) {
      this.productName = productName;
      return this;
    }

    public Builder unitPrice(BigDecimal unitPrice) {
      this.unitPrice = unitPrice;
      return this;
    }

    public Builder quantity(Integer quantity) {
      this.quantity = quantity;
      return this;
    }

    public CartItem build() {
      return new CartItem(this);
    }
  }
}
