package com.example.digigoods.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import java.math.BigDecimal;

/**
 * Request DTO for updating an existing product.
 */
public class UpdateProductRequest {
  private String name;
  private String description;

  @DecimalMin(value = "0.0", inclusive = false, message = "Price must be positive")
  private BigDecimal price;

  private String category;

  @Min(value = 0, message = "Stock quantity must be non-negative")
  private Integer stockQuantity;

  private Boolean active;

  public UpdateProductRequest() {
  }

  private UpdateProductRequest(Builder builder) {
    this.name = builder.name;
    this.description = builder.description;
    this.price = builder.price;
    this.category = builder.category;
    this.stockQuantity = builder.stockQuantity;
    this.active = builder.active;
  }

  public static Builder builder() {
    return new Builder();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public Integer getStockQuantity() {
    return stockQuantity;
  }

  public void setStockQuantity(Integer stockQuantity) {
    this.stockQuantity = stockQuantity;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

  public static class Builder {
    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    private Integer stockQuantity;
    private Boolean active;

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder description(String description) {
      this.description = description;
      return this;
    }

    public Builder price(BigDecimal price) {
      this.price = price;
      return this;
    }

    public Builder category(String category) {
      this.category = category;
      return this;
    }

    public Builder stockQuantity(Integer stockQuantity) {
      this.stockQuantity = stockQuantity;
      return this;
    }

    public Builder active(Boolean active) {
      this.active = active;
      return this;
    }

    public UpdateProductRequest build() {
      return new UpdateProductRequest(this);
    }
  }
}
