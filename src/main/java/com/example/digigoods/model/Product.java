package com.example.digigoods.model;

import java.math.BigDecimal;

/**
 * Domain model representing a product.
 */
public class Product {
  private Long id;
  private String name;
  private String description;
  private BigDecimal price;
  private String category;
  private Integer stockQuantity;
  private Boolean active;

  public Product() {
  }

  private Product(Builder builder) {
    this.id = builder.id;
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

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    private Integer stockQuantity;
    private Boolean active;

    public Builder id(Long id) {
      this.id = id;
      return this;
    }

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

    public Product build() {
      return new Product(this);
    }
  }
}
