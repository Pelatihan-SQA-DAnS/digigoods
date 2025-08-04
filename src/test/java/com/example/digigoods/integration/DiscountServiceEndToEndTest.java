package com.example.digigoods.integration;

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
class DiscountServiceEndToEndTest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  @DisplayName("Complete discount service workflow - End to End Test")
  void givenCompleteWorkflowWhenProcessingDiscountsThenAllFunctionalityWorks() {
    // Step 1: Create multiple coupons with different discount types
    CreateCouponRequest percentageCoupon = CreateCouponRequest.builder()
        .code("PERCENT20")
        .description("20% off on all items")
        .discountType(DiscountType.PERCENTAGE)
        .discountValue(new BigDecimal("20.00"))
        .minimumOrderAmount(new BigDecimal("100.00"))
        .expiryDate(LocalDateTime.now().plusDays(30))
        .usageLimit(100)
        .build();

    CreateCouponRequest fixedAmountCoupon = CreateCouponRequest.builder()
        .code("FIXED10")
        .description("$10 off on orders")
        .discountType(DiscountType.FIXED_AMOUNT)
        .discountValue(new BigDecimal("10.00"))
        .minimumOrderAmount(new BigDecimal("50.00"))
        .expiryDate(LocalDateTime.now().plusDays(15))
        .usageLimit(50)
        .build();

    CreateCouponRequest freeShippingCoupon = CreateCouponRequest.builder()
        .code("FREESHIP")
        .description("Free shipping on all orders")
        .discountType(DiscountType.FREE_SHIPPING)
        .discountValue(new BigDecimal("10.00")) // Free shipping worth $10
        .minimumOrderAmount(new BigDecimal("25.00"))
        .expiryDate(LocalDateTime.now().plusDays(60))
        .usageLimit(200)
        .build();

    // Create all coupons
    ResponseEntity<CouponResponse> percentResponse = restTemplate.postForEntity(
        "http://localhost:" + port + "/api/v1/coupons",
        percentageCoupon,
        CouponResponse.class
    );
    assertThat(percentResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

    ResponseEntity<CouponResponse> fixedResponse = restTemplate.postForEntity(
        "http://localhost:" + port + "/api/v1/coupons",
        fixedAmountCoupon,
        CouponResponse.class
    );
    assertThat(fixedResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

    ResponseEntity<CouponResponse> freeShipResponse = restTemplate.postForEntity(
        "http://localhost:" + port + "/api/v1/coupons",
        freeShippingCoupon,
        CouponResponse.class
    );
    assertThat(freeShipResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

    // Step 2: Retrieve coupons to verify they were created correctly
    ResponseEntity<CouponResponse> retrievedPercent = restTemplate.getForEntity(
        "http://localhost:" + port + "/api/v1/coupons/PERCENT20",
        CouponResponse.class
    );
    assertThat(retrievedPercent.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(retrievedPercent.getBody().getCode()).isEqualTo("PERCENT20");

    // Step 3: Create a shopping cart with multiple items
    List<CartItem> cartItems = List.of(
        CartItem.builder()
            .productId(1L)
            .productName("Gaming Laptop")
            .unitPrice(new BigDecimal("800.00"))
            .quantity(1)
            .build(),
        CartItem.builder()
            .productId(2L)
            .productName("Wireless Mouse")
            .unitPrice(new BigDecimal("25.00"))
            .quantity(2)
            .build(),
        CartItem.builder()
            .productId(3L)
            .productName("Mechanical Keyboard")
            .unitPrice(new BigDecimal("75.00"))
            .quantity(1)
            .build()
    );

    // Step 4: Calculate discount with percentage coupon only
    DiscountCalculationRequest percentOnlyRequest = DiscountCalculationRequest.builder()
        .cartItems(cartItems)
        .couponCodes(List.of("PERCENT20"))
        .build();

    ResponseEntity<DiscountCalculationResponse> percentOnlyResponse = restTemplate.postForEntity(
        "http://localhost:" + port + "/api/v1/discounts/calculate",
        percentOnlyRequest,
        DiscountCalculationResponse.class
    );

    assertThat(percentOnlyResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(percentOnlyResponse.getBody().getOriginalTotal())
        .isEqualTo(new BigDecimal("925.00"));
    assertThat(percentOnlyResponse.getBody().getDiscountAmount())
        .isEqualTo(new BigDecimal("185.00"));
    assertThat(percentOnlyResponse.getBody().getFinalTotal()).isEqualTo(new BigDecimal("740.00"));
    assertThat(percentOnlyResponse.getBody().getAppliedDiscounts()).hasSize(1);

    // Step 5: Calculate discount with multiple coupons
    DiscountCalculationRequest multiCouponRequest = DiscountCalculationRequest.builder()
        .cartItems(cartItems)
        .couponCodes(List.of("PERCENT20", "FIXED10", "FREESHIP"))
        .build();

    ResponseEntity<DiscountCalculationResponse> multiCouponResponse = restTemplate.postForEntity(
        "http://localhost:" + port + "/api/v1/discounts/calculate",
        multiCouponRequest,
        DiscountCalculationResponse.class
    );

    assertThat(multiCouponResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(multiCouponResponse.getBody().getOriginalTotal())
        .isEqualTo(new BigDecimal("925.00"));
    // 20% of 925 = 185, plus $10 fixed, plus $10 free shipping = $205 total discount
    assertThat(multiCouponResponse.getBody().getDiscountAmount())
        .isEqualTo(new BigDecimal("205.00"));
    assertThat(multiCouponResponse.getBody().getFinalTotal()).isEqualTo(new BigDecimal("720.00"));
    assertThat(multiCouponResponse.getBody().getAppliedDiscounts()).hasSize(3);

    // Step 6: Test error handling - try to retrieve non-existent coupon
    ResponseEntity<String> notFoundResponse = restTemplate.getForEntity(
        "http://localhost:" + port + "/api/v1/coupons/NONEXISTENT",
        String.class
    );
    assertThat(notFoundResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    assertThat(notFoundResponse.getBody()).contains("COUPON_NOT_FOUND");
  }
}
