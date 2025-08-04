package com.example.digigoods.service;

import com.example.digigoods.dto.CouponResponse;
import com.example.digigoods.dto.CreateCouponRequest;

/**
 * Service interface for managing coupons.
 */
public interface CouponService {

  /**
   * Creates a new coupon.
   *
   * @param request the coupon creation request
   * @return the created coupon response
   */
  CouponResponse createCoupon(CreateCouponRequest request);

  /**
   * Retrieves a coupon by its code.
   *
   * @param code the coupon code
   * @return the coupon response
   */
  CouponResponse getCouponByCode(String code);
}
