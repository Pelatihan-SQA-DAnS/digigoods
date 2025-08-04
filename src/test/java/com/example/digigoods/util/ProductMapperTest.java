package com.example.digigoods.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.example.digigoods.dto.CartItem;
import com.example.digigoods.model.Product;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductMapperTest {

  @Test
  @DisplayName("Should convert Product to CartItem successfully")
  void givenValidProductWhenToCartItemThenReturnCartItem() {
    // Arrange
    Product product = Product.builder()
        .id(1L)
        .name("Laptop")
        .description("High-performance laptop")
        .price(new BigDecimal("999.99"))
        .category("Electronics")
        .stockQuantity(10)
        .active(true)
        .build();

    Integer quantity = 2;

    // Act
    CartItem cartItem = ProductMapper.toCartItem(product, quantity);

    // Assert
    assertNotNull(cartItem);
    assertEquals(1L, cartItem.getProductId());
    assertEquals("Laptop", cartItem.getProductName());
    assertEquals(new BigDecimal("999.99"), cartItem.getUnitPrice());
    assertEquals(2, cartItem.getQuantity());
  }

  @Test
  @DisplayName("Should return null when Product is null")
  void givenNullProductWhenToCartItemThenReturnNull() {
    // Arrange
    Product product = null;
    Integer quantity = 1;

    // Act
    CartItem cartItem = ProductMapper.toCartItem(product, quantity);

    // Assert
    assertNull(cartItem);
  }

  @Test
  @DisplayName("Should create CartItem with provided details")
  void givenProductDetailsWhenCreateCartItemThenReturnCartItem() {
    // Arrange
    Long productId = 2L;
    String productName = "Mouse";
    BigDecimal unitPrice = new BigDecimal("29.99");
    Integer quantity = 3;

    // Act
    CartItem cartItem = ProductMapper.createCartItem(productId, productName, unitPrice, quantity);

    // Assert
    assertNotNull(cartItem);
    assertEquals(2L, cartItem.getProductId());
    assertEquals("Mouse", cartItem.getProductName());
    assertEquals(new BigDecimal("29.99"), cartItem.getUnitPrice());
    assertEquals(3, cartItem.getQuantity());
  }
}
