package com.example.digigoods.service;

import com.example.digigoods.dto.DiscountCalculationRequest;
import com.example.digigoods.dto.DiscountCalculationResponse;

/**
 * Service interface for discount calculations.
 */
public interface DiscountCalculationService {

  /**
   * Calculates discounts for a shopping cart with applied coupon codes.
   *
   * @param request the discount calculation request
   * @return the discount calculation response
   */
  DiscountCalculationResponse calculateDiscount(DiscountCalculationRequest request);
}
