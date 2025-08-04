package com.example.digigoods.util;

import com.example.digigoods.dto.CartItem;
import com.example.digigoods.model.Product;

/**
 * Utility class for mapping between Product domain model and related DTOs.
 */
public final class ProductMapper {

  private ProductMapper() {
    // Utility class - prevent instantiation
  }

  /**
   * Converts a Product domain model to a CartItem DTO.
   * This is useful when adding products to a shopping cart.
   *
   * @param product the product to convert
   * @param quantity the quantity of the product in the cart
   * @return CartItem DTO representing the product in cart context
   */
  public static CartItem toCartItem(Product product, Integer quantity) {
    if (product == null) {
      return null;
    }

    return CartItem.builder()
        .productId(product.getId())
        .productName(product.getName())
        .unitPrice(product.getPrice())
        .quantity(quantity)
        .build();
  }

  /**
   * Creates a CartItem DTO with product information.
   * This is useful when you have product details but need cart representation.
   *
   * @param productId the product ID
   * @param productName the product name
   * @param unitPrice the unit price
   * @param quantity the quantity
   * @return CartItem DTO
   */
  public static CartItem createCartItem(Long productId, String productName, 
                                       java.math.BigDecimal unitPrice, Integer quantity) {
    return CartItem.builder()
        .productId(productId)
        .productName(productName)
        .unitPrice(unitPrice)
        .quantity(quantity)
        .build();
  }
}
