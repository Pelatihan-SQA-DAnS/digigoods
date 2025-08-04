package com.example.digigoods.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.digigoods.dto.CartItem;
import com.example.digigoods.dto.CouponResponse;
import com.example.digigoods.dto.CreateCouponRequest;
import com.example.digigoods.dto.DiscountCalculationRequest;
import com.example.digigoods.dto.DiscountCalculationResponse;
import com.example.digigoods.model.DiscountType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CouponControllerIntegrationTest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  @DisplayName("Should create a percentage discount coupon successfully - Integration Test")
  void givenValidPercentageCouponRequestWhenCreateCouponThenReturnCreatedCouponIntegration() {
    // Arrange
    CreateCouponRequest request = CreateCouponRequest.builder()
        .code("SAVE20")
        .description("20% off on all items")
        .discountType(DiscountType.PERCENTAGE)
        .discountValue(new BigDecimal("20.00"))
        .minimumOrderAmount(new BigDecimal("100.00"))
        .expiryDate(LocalDateTime.now().plusDays(30))
        .usageLimit(100)
        .build();

    // Act
    ResponseEntity<CouponResponse> response = restTemplate.postForEntity(
        "http://localhost:" + port + "/api/v1/coupons",
        request,
        CouponResponse.class
    );

    // Assert
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getId()).isNotNull();
    assertThat(response.getBody().getCode()).isEqualTo("SAVE20");
    assertThat(response.getBody().getDescription()).isEqualTo("20% off on all items");
    assertThat(response.getBody().getDiscountType()).isEqualTo(DiscountType.PERCENTAGE);
    assertThat(response.getBody().getDiscountValue()).isEqualTo(new BigDecimal("20.00"));
    assertThat(response.getBody().getMinimumOrderAmount()).isEqualTo(new BigDecimal("100.00"));
    assertThat(response.getBody().getUsageLimit()).isEqualTo(100);
    assertThat(response.getBody().getUsageCount()).isEqualTo(0);
    assertThat(response.getBody().getActive()).isTrue();
  }

  @Test
  @DisplayName("Should retrieve a coupon by code successfully - Integration Test")
  void givenValidCouponCodeWhenGetCouponThenReturnCouponIntegration() {
    // Arrange - First create a coupon
    CreateCouponRequest createRequest = CreateCouponRequest.builder()
        .code("GET20")
        .description("20% off for retrieval test")
        .discountType(DiscountType.PERCENTAGE)
        .discountValue(new BigDecimal("20.00"))
        .minimumOrderAmount(new BigDecimal("50.00"))
        .expiryDate(LocalDateTime.now().plusDays(15))
        .usageLimit(50)
        .build();

    restTemplate.postForEntity(
        "http://localhost:" + port + "/api/v1/coupons",
        createRequest,
        CouponResponse.class
    );

    // Act - Retrieve the coupon
    ResponseEntity<CouponResponse> response = restTemplate.getForEntity(
        "http://localhost:" + port + "/api/v1/coupons/GET20",
        CouponResponse.class
    );

    // Assert
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getCode()).isEqualTo("GET20");
    assertThat(response.getBody().getDescription()).isEqualTo("20% off for retrieval test");
    assertThat(response.getBody().getDiscountType()).isEqualTo(DiscountType.PERCENTAGE);
    assertThat(response.getBody().getDiscountValue()).isEqualTo(new BigDecimal("20.00"));
    assertThat(response.getBody().getMinimumOrderAmount()).isEqualTo(new BigDecimal("50.00"));
    assertThat(response.getBody().getUsageLimit()).isEqualTo(50);
    assertThat(response.getBody().getUsageCount()).isEqualTo(0);
    assertThat(response.getBody().getActive()).isTrue();
  }

  @Test
  @DisplayName("Should calculate discount for cart successfully - Integration Test")
  void givenValidDiscountRequestWhenCalculateDiscountThenReturnCalculatedDiscountIntegration() {
    // Arrange - First create a coupon
    CreateCouponRequest createRequest = CreateCouponRequest.builder()
        .code("CALC20")
        .description("20% off for calculation test")
        .discountType(DiscountType.PERCENTAGE)
        .discountValue(new BigDecimal("20.00"))
        .minimumOrderAmount(new BigDecimal("50.00"))
        .expiryDate(LocalDateTime.now().plusDays(15))
        .usageLimit(50)
        .build();

    restTemplate.postForEntity(
        "http://localhost:" + port + "/api/v1/coupons",
        createRequest,
        CouponResponse.class
    );

    // Prepare discount calculation request
    List<CartItem> cartItems = List.of(
        CartItem.builder()
            .productId(1L)
            .productName("Product 1")
            .unitPrice(new BigDecimal("50.00"))
            .quantity(2)
            .build(),
        CartItem.builder()
            .productId(2L)
            .productName("Product 2")
            .unitPrice(new BigDecimal("30.00"))
            .quantity(1)
            .build()
    );

    DiscountCalculationRequest discountRequest = DiscountCalculationRequest.builder()
        .cartItems(cartItems)
        .couponCodes(List.of("CALC20"))
        .build();

    // Act - Calculate discount
    ResponseEntity<DiscountCalculationResponse> response = restTemplate.postForEntity(
        "http://localhost:" + port + "/api/v1/discounts/calculate",
        discountRequest,
        DiscountCalculationResponse.class
    );

    // Assert
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getOriginalTotal()).isEqualTo(new BigDecimal("130.00"));
    assertThat(response.getBody().getDiscountAmount()).isEqualTo(new BigDecimal("26.00"));
    assertThat(response.getBody().getFinalTotal()).isEqualTo(new BigDecimal("104.00"));
    assertThat(response.getBody().getAppliedDiscounts()).hasSize(1);
    assertThat(response.getBody().getAppliedDiscounts().get(0).getCouponCode())
        .isEqualTo("CALC20");
    assertThat(response.getBody().getAppliedDiscounts().get(0).getDiscountAmount())
        .isEqualTo(new BigDecimal("26.00"));
  }

  @Test
  @DisplayName("Should return validation error when coupon code is null - Integration Test")
  void givenNullCouponCodeWhenCreateCouponThenReturnValidationErrorIntegration() {
    // Arrange
    CreateCouponRequest request = CreateCouponRequest.builder()
        .code(null)
        .description("Test coupon")
        .discountType(DiscountType.PERCENTAGE)
        .discountValue(new BigDecimal("20.00"))
        .minimumOrderAmount(new BigDecimal("100.00"))
        .expiryDate(LocalDateTime.now().plusDays(30))
        .usageLimit(100)
        .build();

    // Act
    ResponseEntity<String> response = restTemplate.postForEntity(
        "http://localhost:" + port + "/api/v1/coupons",
        request,
        String.class
    );

    // Assert
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody()).contains("VALIDATION_ERROR");
  }
}
