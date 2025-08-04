package com.example.digigoods.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.digigoods.dto.CouponResponse;
import com.example.digigoods.dto.CreateCouponRequest;
import com.example.digigoods.exception.CouponNotFoundException;
import com.example.digigoods.model.DiscountType;
import com.example.digigoods.service.CouponService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CouponController.class)
class CouponControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  private CouponService couponService;

  @Test
  @DisplayName("Should create a percentage discount coupon successfully")
  void givenValidPercentageCouponRequestWhenCreateCouponThenReturnCreatedCoupon()
      throws Exception {
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

    CouponResponse expectedResponse = CouponResponse.builder()
        .id(1L)
        .code("SAVE20")
        .description("20% off on all items")
        .discountType(DiscountType.PERCENTAGE)
        .discountValue(new BigDecimal("20.00"))
        .minimumOrderAmount(new BigDecimal("100.00"))
        .expiryDate(request.getExpiryDate())
        .usageLimit(100)
        .usageCount(0)
        .active(true)
        .build();

    when(couponService.createCoupon(any(CreateCouponRequest.class)))
        .thenReturn(expectedResponse);

    // Act & Assert
    mockMvc.perform(post("/api/v1/coupons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(1L))
        .andExpect(jsonPath("$.code").value("SAVE20"))
        .andExpect(jsonPath("$.description").value("20% off on all items"))
        .andExpect(jsonPath("$.discountType").value("PERCENTAGE"))
        .andExpect(jsonPath("$.discountValue").value(20.00))
        .andExpect(jsonPath("$.minimumOrderAmount").value(100.00))
        .andExpect(jsonPath("$.usageLimit").value(100))
        .andExpect(jsonPath("$.usageCount").value(0))
        .andExpect(jsonPath("$.active").value(true));
  }

  @Test
  @DisplayName("Should retrieve a coupon by code successfully")
  void givenValidCouponCodeWhenGetCouponThenReturnCoupon() throws Exception {
    // Arrange
    CouponResponse expectedResponse = CouponResponse.builder()
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

    when(couponService.getCouponByCode(eq("SAVE20"))).thenReturn(expectedResponse);

    // Act & Assert
    mockMvc.perform(get("/api/v1/coupons/SAVE20"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1L))
        .andExpect(jsonPath("$.code").value("SAVE20"))
        .andExpect(jsonPath("$.description").value("20% off on all items"))
        .andExpect(jsonPath("$.discountType").value("PERCENTAGE"))
        .andExpect(jsonPath("$.discountValue").value(20.00))
        .andExpect(jsonPath("$.minimumOrderAmount").value(100.00))
        .andExpect(jsonPath("$.usageLimit").value(100))
        .andExpect(jsonPath("$.usageCount").value(0))
        .andExpect(jsonPath("$.active").value(true));
  }

  @Test
  @DisplayName("Should return 404 when coupon not found")
  void givenInvalidCouponCodeWhenGetCouponThenReturnNotFound() throws Exception {
    // Arrange
    when(couponService.getCouponByCode(eq("INVALID")))
        .thenThrow(new CouponNotFoundException("Coupon not found with code: INVALID"));

    // Act & Assert
    mockMvc.perform(get("/api/v1/coupons/INVALID"))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.error").value("COUPON_NOT_FOUND"))
        .andExpect(jsonPath("$.message").value("Coupon not found with code: INVALID"));
  }
}
