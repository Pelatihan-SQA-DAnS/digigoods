package com.example.digigoods.controller;

import com.example.digigoods.dto.DiscountCalculationRequest;
import com.example.digigoods.dto.DiscountCalculationResponse;
import com.example.digigoods.service.DiscountCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for discount calculations.
 */
@RestController
@RequestMapping("/api/v1/discounts")
public class DiscountController {

  private final DiscountCalculationService discountCalculationService;

  @Autowired
  public DiscountController(DiscountCalculationService discountCalculationService) {
    this.discountCalculationService = discountCalculationService;
  }

  /**
   * Calculates discounts for a shopping cart with applied coupon codes.
   *
   * @param request the discount calculation request
   * @return the discount calculation response
   */
  @PostMapping("/calculate")
  public ResponseEntity<DiscountCalculationResponse> calculateDiscount(
      @RequestBody DiscountCalculationRequest request) {
    DiscountCalculationResponse response = discountCalculationService.calculateDiscount(request);
    return ResponseEntity.ok(response);
  }
}
