package com.example.digigoods.controller;

import com.example.digigoods.dto.CouponResponse;
import com.example.digigoods.dto.CreateCouponRequest;
import com.example.digigoods.service.CouponService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing coupons.
 */
@RestController
@RequestMapping("/api/v1/coupons")
public class CouponController {

  private final CouponService couponService;

  @Autowired
  public CouponController(CouponService couponService) {
    this.couponService = couponService;
  }

  /**
   * Creates a new coupon.
   *
   * @param request the coupon creation request
   * @return the created coupon response
   */
  @PostMapping
  public ResponseEntity<CouponResponse> createCoupon(
      @Valid @RequestBody CreateCouponRequest request) {
    CouponResponse response = couponService.createCoupon(request);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  /**
   * Retrieves a coupon by its code.
   *
   * @param code the coupon code
   * @return the coupon response
   */
  @GetMapping("/{code}")
  public ResponseEntity<CouponResponse> getCouponByCode(@PathVariable String code) {
    CouponResponse response = couponService.getCouponByCode(code);
    return ResponseEntity.ok(response);
  }
}
