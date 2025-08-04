package com.example.digigoods.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.digigoods.dto.CreateProductRequest;
import com.example.digigoods.dto.ProductResponse;
import com.example.digigoods.dto.UpdateProductRequest;
import com.example.digigoods.exception.ProductNotFoundException;
import com.example.digigoods.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  private ProductService productService;

  @Test
  @DisplayName("Should create a product successfully")
  void givenValidCreateProductRequestWhenCreateProductThenReturnCreatedProduct()
      throws Exception {
    // Arrange
    CreateProductRequest request = CreateProductRequest.builder()
        .name("Laptop")
        .description("High-performance laptop")
        .price(new BigDecimal("999.99"))
        .category("Electronics")
        .stockQuantity(10)
        .build();

    ProductResponse expectedResponse = ProductResponse.builder()
        .id(1L)
        .name("Laptop")
        .description("High-performance laptop")
        .price(new BigDecimal("999.99"))
        .category("Electronics")
        .stockQuantity(10)
        .active(true)
        .build();

    when(productService.createProduct(any(CreateProductRequest.class)))
        .thenReturn(expectedResponse);

    // Act & Assert
    mockMvc.perform(post("/api/v1/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(1L))
        .andExpect(jsonPath("$.name").value("Laptop"))
        .andExpect(jsonPath("$.description").value("High-performance laptop"))
        .andExpect(jsonPath("$.price").value(999.99))
        .andExpect(jsonPath("$.category").value("Electronics"))
        .andExpect(jsonPath("$.stockQuantity").value(10))
        .andExpect(jsonPath("$.active").value(true));

    verify(productService).createProduct(any(CreateProductRequest.class));
  }

  @Test
  @DisplayName("Should retrieve a product by ID successfully")
  void givenValidProductIdWhenGetProductThenReturnProduct() throws Exception {
    // Arrange
    Long productId = 1L;
    ProductResponse expectedResponse = ProductResponse.builder()
        .id(productId)
        .name("Laptop")
        .description("High-performance laptop")
        .price(new BigDecimal("999.99"))
        .category("Electronics")
        .stockQuantity(10)
        .active(true)
        .build();

    when(productService.getProductById(eq(productId))).thenReturn(expectedResponse);

    // Act & Assert
    mockMvc.perform(get("/api/v1/products/{id}", productId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(productId))
        .andExpect(jsonPath("$.name").value("Laptop"))
        .andExpect(jsonPath("$.description").value("High-performance laptop"))
        .andExpect(jsonPath("$.price").value(999.99))
        .andExpect(jsonPath("$.category").value("Electronics"))
        .andExpect(jsonPath("$.stockQuantity").value(10))
        .andExpect(jsonPath("$.active").value(true));

    verify(productService).getProductById(productId);
  }

  @Test
  @DisplayName("Should return 404 when product not found")
  void givenInvalidProductIdWhenGetProductThenReturnNotFound() throws Exception {
    // Arrange
    Long productId = 999L;
    when(productService.getProductById(eq(productId)))
        .thenThrow(new ProductNotFoundException("Product not found with id: " + productId));

    // Act & Assert
    mockMvc.perform(get("/api/v1/products/{id}", productId))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.error").value("PRODUCT_NOT_FOUND"))
        .andExpect(jsonPath("$.message").value("Product not found with id: " + productId));

    verify(productService).getProductById(productId);
  }

  @Test
  @DisplayName("Should retrieve all products successfully")
  void givenProductsExistWhenGetAllProductsThenReturnProductList() throws Exception {
    // Arrange
    List<ProductResponse> expectedResponses = Arrays.asList(
        ProductResponse.builder()
            .id(1L)
            .name("Laptop")
            .description("High-performance laptop")
            .price(new BigDecimal("999.99"))
            .category("Electronics")
            .stockQuantity(10)
            .active(true)
            .build(),
        ProductResponse.builder()
            .id(2L)
            .name("Mouse")
            .description("Wireless mouse")
            .price(new BigDecimal("29.99"))
            .category("Electronics")
            .stockQuantity(50)
            .active(true)
            .build()
    );

    when(productService.getAllProducts()).thenReturn(expectedResponses);

    // Act & Assert
    mockMvc.perform(get("/api/v1/products"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$.length()").value(2))
        .andExpect(jsonPath("$[0].name").value("Laptop"))
        .andExpect(jsonPath("$[1].name").value("Mouse"));

    verify(productService).getAllProducts();
  }

  @Test
  @DisplayName("Should update a product successfully")
  void givenValidUpdateRequestWhenUpdateProductThenReturnUpdatedProduct() throws Exception {
    // Arrange
    Long productId = 1L;
    UpdateProductRequest request = UpdateProductRequest.builder()
        .name("Updated Laptop")
        .description("Updated high-performance laptop")
        .price(new BigDecimal("1099.99"))
        .category("Electronics")
        .stockQuantity(15)
        .active(true)
        .build();

    ProductResponse expectedResponse = ProductResponse.builder()
        .id(productId)
        .name("Updated Laptop")
        .description("Updated high-performance laptop")
        .price(new BigDecimal("1099.99"))
        .category("Electronics")
        .stockQuantity(15)
        .active(true)
        .build();

    when(productService.updateProduct(eq(productId), any(UpdateProductRequest.class)))
        .thenReturn(expectedResponse);

    // Act & Assert
    mockMvc.perform(put("/api/v1/products/{id}", productId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(productId))
        .andExpect(jsonPath("$.name").value("Updated Laptop"))
        .andExpect(jsonPath("$.description").value("Updated high-performance laptop"))
        .andExpect(jsonPath("$.price").value(1099.99))
        .andExpect(jsonPath("$.category").value("Electronics"))
        .andExpect(jsonPath("$.stockQuantity").value(15))
        .andExpect(jsonPath("$.active").value(true));

    verify(productService).updateProduct(eq(productId), any(UpdateProductRequest.class));
  }

  @Test
  @DisplayName("Should delete a product successfully")
  void givenValidProductIdWhenDeleteProductThenReturnNoContent() throws Exception {
    // Arrange
    Long productId = 1L;

    // Act & Assert
    mockMvc.perform(delete("/api/v1/products/{id}", productId))
        .andExpect(status().isNoContent());

    verify(productService).deleteProduct(productId);
  }

  @Test
  @DisplayName("Should return 404 when deleting non-existent product")
  void givenInvalidProductIdWhenDeleteProductThenReturnNotFound() throws Exception {
    // Arrange
    Long productId = 999L;
    doThrow(new ProductNotFoundException("Product not found with id: " + productId))
        .when(productService).deleteProduct(productId);

    // Act & Assert
    mockMvc.perform(delete("/api/v1/products/{id}", productId))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.error").value("PRODUCT_NOT_FOUND"))
        .andExpect(jsonPath("$.message").value("Product not found with id: " + productId));

    verify(productService).deleteProduct(productId);
  }

  @Test
  @DisplayName("Should return validation error when product name is blank")
  void givenBlankProductNameWhenCreateProductThenReturnValidationError() throws Exception {
    // Arrange
    CreateProductRequest request = CreateProductRequest.builder()
        .name("")
        .description("High-performance laptop")
        .price(new BigDecimal("999.99"))
        .category("Electronics")
        .stockQuantity(10)
        .build();

    // Act & Assert
    mockMvc.perform(post("/api/v1/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.error").value("VALIDATION_ERROR"));
  }
}
