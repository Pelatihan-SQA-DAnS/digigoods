package com.example.digigoods.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.example.digigoods.dto.AppliedDiscount;
import com.example.digigoods.dto.CartItem;
import com.example.digigoods.dto.CouponResponse;
import com.example.digigoods.dto.DiscountCalculationRequest;
import com.example.digigoods.dto.DiscountCalculationResponse;
import com.example.digigoods.model.DiscountType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DiscountCalculationServiceTest {

  @Mock
  private CouponService couponService;

  @InjectMocks
  private DiscountCalculationServiceImpl discountCalculationService;

  @Test
  @DisplayName("Should calculate percentage discount correctly")
  void givenCartWithPercentageCouponWhenCalculateDiscountThenReturnCorrectDiscount() {
    // Arrange
    List<CartItem> cartItems = List.of(
        CartItem.builder()
            .productId("PROD1")
            .productName("Product 1")
            .unitPrice(new BigDecimal("50.00"))
            .quantity(2)
            .build(),
        CartItem.builder()
            .productId("PROD2")
            .productName("Product 2")
            .unitPrice(new BigDecimal("30.00"))
            .quantity(1)
            .build()
    );

    DiscountCalculationRequest request = DiscountCalculationRequest.builder()
        .cartItems(cartItems)
        .couponCodes(List.of("SAVE20"))
        .build();

    CouponResponse coupon = CouponResponse.builder()
        .id(1L)
        .code("SAVE20")
        .description("20% off on all items")
        .discountType(DiscountType.PERCENTAGE)
        .discountValue(new BigDecimal("20.00"))
        .minimumOrderAmount(new BigDecimal("100.00"))
        .expiryDate(LocalDateTime.now().plusDays(30))
        .usageLimit(100)
        .usageCount(0)
        .active(true)
        .build();

    when(couponService.getCouponByCode(eq("SAVE20"))).thenReturn(coupon);

    // Act
    DiscountCalculationResponse response = discountCalculationService.calculateDiscount(request);

    // Assert
    assertThat(response.getOriginalTotal()).isEqualTo(new BigDecimal("130.00"));
    assertThat(response.getDiscountAmount()).isEqualTo(new BigDecimal("26.00"));
    assertThat(response.getFinalTotal()).isEqualTo(new BigDecimal("104.00"));
    assertThat(response.getAppliedDiscounts()).hasSize(1);

    AppliedDiscount appliedDiscount = response.getAppliedDiscounts().get(0);
    assertThat(appliedDiscount.getCouponCode()).isEqualTo("SAVE20");
    assertThat(appliedDiscount.getDescription()).isEqualTo("20% off on all items");
    assertThat(appliedDiscount.getDiscountType()).isEqualTo(DiscountType.PERCENTAGE);
    assertThat(appliedDiscount.getDiscountValue()).isEqualTo(new BigDecimal("20.00"));
    assertThat(appliedDiscount.getDiscountAmount()).isEqualTo(new BigDecimal("26.00"));
  }
}
