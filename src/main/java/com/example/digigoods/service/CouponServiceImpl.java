package com.example.digigoods.service;

import com.example.digigoods.dto.CouponResponse;
import com.example.digigoods.dto.CreateCouponRequest;
import com.example.digigoods.exception.CouponNotFoundException;
import com.example.digigoods.model.Coupon;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;

/**
 * Implementation of CouponService for managing coupons.
 */
@Service
public class CouponServiceImpl implements CouponService {

  private final ConcurrentHashMap<String, Coupon> coupons = new ConcurrentHashMap<>();
  private final AtomicLong idGenerator = new AtomicLong(1);

  @Override
  public CouponResponse createCoupon(CreateCouponRequest request) {
    Long id = idGenerator.getAndIncrement();

    Coupon coupon = Coupon.builder()
        .id(id)
        .code(request.getCode())
        .description(request.getDescription())
        .discountType(request.getDiscountType())
        .discountValue(request.getDiscountValue())
        .minimumOrderAmount(request.getMinimumOrderAmount())
        .expiryDate(request.getExpiryDate())
        .usageLimit(request.getUsageLimit())
        .usageCount(0)
        .active(true)
        .build();

    coupons.put(request.getCode(), coupon);

    return CouponResponse.builder()
        .id(coupon.getId())
        .code(coupon.getCode())
        .description(coupon.getDescription())
        .discountType(coupon.getDiscountType())
        .discountValue(coupon.getDiscountValue())
        .minimumOrderAmount(coupon.getMinimumOrderAmount())
        .expiryDate(coupon.getExpiryDate())
        .usageLimit(coupon.getUsageLimit())
        .usageCount(coupon.getUsageCount())
        .active(coupon.getActive())
        .build();
  }

  @Override
  public CouponResponse getCouponByCode(String code) {
    Coupon coupon = coupons.get(code);
    if (coupon == null) {
      throw new CouponNotFoundException("Coupon not found with code: " + code);
    }

    return CouponResponse.builder()
        .id(coupon.getId())
        .code(coupon.getCode())
        .description(coupon.getDescription())
        .discountType(coupon.getDiscountType())
        .discountValue(coupon.getDiscountValue())
        .minimumOrderAmount(coupon.getMinimumOrderAmount())
        .expiryDate(coupon.getExpiryDate())
        .usageLimit(coupon.getUsageLimit())
        .usageCount(coupon.getUsageCount())
        .active(coupon.getActive())
        .build();
  }
}
