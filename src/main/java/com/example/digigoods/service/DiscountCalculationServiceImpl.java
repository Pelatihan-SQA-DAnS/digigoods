package com.example.digigoods.service;

import com.example.digigoods.dto.AppliedDiscount;
import com.example.digigoods.dto.CartItem;
import com.example.digigoods.dto.CouponResponse;
import com.example.digigoods.dto.DiscountCalculationRequest;
import com.example.digigoods.dto.DiscountCalculationResponse;
import com.example.digigoods.model.DiscountType;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of DiscountCalculationService.
 */
@Service
public class DiscountCalculationServiceImpl implements DiscountCalculationService {

  private final CouponService couponService;

  @Autowired
  public DiscountCalculationServiceImpl(CouponService couponService) {
    this.couponService = couponService;
  }

  @Override
  public DiscountCalculationResponse calculateDiscount(DiscountCalculationRequest request) {
    // Calculate original total
    BigDecimal originalTotal = calculateOriginalTotal(request.getCartItems());

    // Apply discounts
    List<AppliedDiscount> appliedDiscounts = new ArrayList<>();
    BigDecimal totalDiscountAmount = BigDecimal.ZERO;

    for (String couponCode : request.getCouponCodes()) {
      CouponResponse coupon = couponService.getCouponByCode(couponCode);

      if (isValidCoupon(coupon, originalTotal)) {
        BigDecimal discountAmount = calculateDiscountAmount(coupon, originalTotal);
        
        AppliedDiscount appliedDiscount = AppliedDiscount.builder()
            .couponCode(coupon.getCode())
            .description(coupon.getDescription())
            .discountType(coupon.getDiscountType())
            .discountValue(coupon.getDiscountValue())
            .discountAmount(discountAmount)
            .build();

        appliedDiscounts.add(appliedDiscount);
        totalDiscountAmount = totalDiscountAmount.add(discountAmount);
      }
    }

    BigDecimal finalTotal = originalTotal.subtract(totalDiscountAmount);
    if (finalTotal.compareTo(BigDecimal.ZERO) < 0) {
      finalTotal = BigDecimal.ZERO;
    }

    return DiscountCalculationResponse.builder()
        .originalTotal(originalTotal)
        .discountAmount(totalDiscountAmount)
        .finalTotal(finalTotal)
        .appliedDiscounts(appliedDiscounts)
        .build();
  }

  private BigDecimal calculateOriginalTotal(List<CartItem> cartItems) {
    return cartItems.stream()
        .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  private boolean isValidCoupon(CouponResponse coupon, BigDecimal orderTotal) {
    // Check if coupon is active
    if (!coupon.getActive()) {
      return false;
    }

    // Check if coupon has expired
    if (coupon.getExpiryDate() != null && coupon.getExpiryDate().isBefore(LocalDateTime.now())) {
      return false;
    }

    // Check minimum order amount
    if (coupon.getMinimumOrderAmount() != null 
        && orderTotal.compareTo(coupon.getMinimumOrderAmount()) < 0) {
      return false;
    }

    // Check usage limit
    if (coupon.getUsageLimit() != null && coupon.getUsageCount() >= coupon.getUsageLimit()) {
      return false;
    }

    return true;
  }

  private BigDecimal calculateDiscountAmount(CouponResponse coupon, BigDecimal orderTotal) {
    if (coupon.getDiscountType() == DiscountType.PERCENTAGE) {
      return orderTotal.multiply(coupon.getDiscountValue())
          .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    } else if (coupon.getDiscountType() == DiscountType.FIXED_AMOUNT) {
      return coupon.getDiscountValue();
    } else if (coupon.getDiscountType() == DiscountType.FREE_SHIPPING) {
      // For now, assume free shipping is worth $10
      return new BigDecimal("10.00");
    }

    return BigDecimal.ZERO;
  }
}
