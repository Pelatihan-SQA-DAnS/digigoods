package com.example.digigoods.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Request DTO for creating a new product.
 */
public class CreateProductRequest {
  @NotBlank(message = "Product name is required")
  private String name;

  @NotBlank(message = "Description is required")
  private String description;

  @NotNull(message = "Price is required")
  @DecimalMin(value = "0.0", inclusive = false, message = "Price must be positive")
  private BigDecimal price;

  @NotBlank(message = "Category is required")
  private String category;

  @NotNull(message = "Stock quantity is required")
  @Min(value = 0, message = "Stock quantity must be non-negative")
  private Integer stockQuantity;

  public CreateProductRequest() {
  }

  private CreateProductRequest(Builder builder) {
    this.name = builder.name;
    this.description = builder.description;
    this.price = builder.price;
    this.category = builder.category;
    this.stockQuantity = builder.stockQuantity;
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

  public static class Builder {
    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    private Integer stockQuantity;

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

    public CreateProductRequest build() {
      return new CreateProductRequest(this);
    }
  }
}
