package com.example.digigoods.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.digigoods.dto.AppliedDiscount;
import com.example.digigoods.dto.CartItem;
import com.example.digigoods.dto.DiscountCalculationRequest;
import com.example.digigoods.dto.DiscountCalculationResponse;
import com.example.digigoods.model.DiscountType;
import com.example.digigoods.service.DiscountCalculationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(DiscountController.class)
class DiscountControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  private DiscountCalculationService discountCalculationService;

  @Test
  @DisplayName("Should calculate discount for cart successfully")
  void givenValidDiscountRequestWhenCalculateDiscountThenReturnCalculatedDiscount()
      throws Exception {
    // Arrange
    List<CartItem> cartItems = List.of(
        CartItem.builder()
            .productId(1L)
            .productName("Product 1")
            .unitPrice(new BigDecimal("50.00"))
            .quantity(2)
            .build()
    );

    DiscountCalculationRequest request = DiscountCalculationRequest.builder()
        .cartItems(cartItems)
        .couponCodes(List.of("SAVE20"))
        .build();

    List<AppliedDiscount> appliedDiscounts = List.of(
        AppliedDiscount.builder()
            .couponCode("SAVE20")
            .description("20% off on all items")
            .discountType(DiscountType.PERCENTAGE)
            .discountValue(new BigDecimal("20.00"))
            .discountAmount(new BigDecimal("20.00"))
            .build()
    );

    DiscountCalculationResponse expectedResponse = DiscountCalculationResponse.builder()
        .originalTotal(new BigDecimal("100.00"))
        .discountAmount(new BigDecimal("20.00"))
        .finalTotal(new BigDecimal("80.00"))
        .appliedDiscounts(appliedDiscounts)
        .build();

    when(discountCalculationService.calculateDiscount(any(DiscountCalculationRequest.class)))
        .thenReturn(expectedResponse);

    // Act & Assert
    mockMvc.perform(post("/api/v1/discounts/calculate")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.originalTotal").value(100.00))
        .andExpect(jsonPath("$.discountAmount").value(20.00))
        .andExpect(jsonPath("$.finalTotal").value(80.00))
        .andExpect(jsonPath("$.appliedDiscounts").isArray())
        .andExpect(jsonPath("$.appliedDiscounts[0].couponCode").value("SAVE20"))
        .andExpect(jsonPath("$.appliedDiscounts[0].description").value("20% off on all items"))
        .andExpect(jsonPath("$.appliedDiscounts[0].discountType").value("PERCENTAGE"))
        .andExpect(jsonPath("$.appliedDiscounts[0].discountValue").value(20.00))
        .andExpect(jsonPath("$.appliedDiscounts[0].discountAmount").value(20.00));
  }
}
