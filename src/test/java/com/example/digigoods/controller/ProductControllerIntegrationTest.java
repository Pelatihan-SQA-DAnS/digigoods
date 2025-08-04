package com.example.digigoods.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.digigoods.dto.CreateProductRequest;
import com.example.digigoods.dto.ProductResponse;
import com.example.digigoods.dto.UpdateProductRequest;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerIntegrationTest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  @DisplayName("Should create a product successfully - Integration Test")
  void givenValidCreateProductRequestWhenCreateProductThenReturnCreatedProductIntegration() {
    // Arrange
    CreateProductRequest request = CreateProductRequest.builder()
        .name("Laptop")
        .description("High-performance laptop")
        .price(new BigDecimal("999.99"))
        .category("Electronics")
        .stockQuantity(10)
        .build();

    // Act
    ResponseEntity<ProductResponse> response = restTemplate.postForEntity(
        "http://localhost:" + port + "/api/v1/products",
        request,
        ProductResponse.class
    );

    // Assert
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals("Laptop", response.getBody().getName());
    assertEquals("High-performance laptop", response.getBody().getDescription());
    assertEquals(new BigDecimal("999.99"), response.getBody().getPrice());
    assertEquals("Electronics", response.getBody().getCategory());
    assertEquals(10, response.getBody().getStockQuantity());
    assertTrue(response.getBody().getActive());
    assertNotNull(response.getBody().getId());
  }

  @Test
  @DisplayName("Should retrieve a product by ID successfully - Integration Test")
  void givenValidProductIdWhenGetProductThenReturnProductIntegration() {
    // Arrange - First create a product
    CreateProductRequest createRequest = CreateProductRequest.builder()
        .name("Mouse")
        .description("Wireless mouse")
        .price(new BigDecimal("29.99"))
        .category("Electronics")
        .stockQuantity(50)
        .build();

    ResponseEntity<ProductResponse> createResponse = restTemplate.postForEntity(
        "http://localhost:" + port + "/api/v1/products",
        createRequest,
        ProductResponse.class
    );

    Long productId = createResponse.getBody().getId();

    // Act
    ResponseEntity<ProductResponse> response = restTemplate.getForEntity(
        "http://localhost:" + port + "/api/v1/products/" + productId,
        ProductResponse.class
    );

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(productId, response.getBody().getId());
    assertEquals("Mouse", response.getBody().getName());
    assertEquals("Wireless mouse", response.getBody().getDescription());
    assertEquals(new BigDecimal("29.99"), response.getBody().getPrice());
    assertEquals("Electronics", response.getBody().getCategory());
    assertEquals(50, response.getBody().getStockQuantity());
    assertTrue(response.getBody().getActive());
  }

  @Test
  @DisplayName("Should retrieve all products successfully - Integration Test")
  void givenProductsExistWhenGetAllProductsThenReturnProductListIntegration() {
    // Arrange - Create multiple products
    CreateProductRequest request1 = CreateProductRequest.builder()
        .name("Keyboard")
        .description("Mechanical keyboard")
        .price(new BigDecimal("79.99"))
        .category("Electronics")
        .stockQuantity(25)
        .build();

    CreateProductRequest request2 = CreateProductRequest.builder()
        .name("Monitor")
        .description("4K monitor")
        .price(new BigDecimal("299.99"))
        .category("Electronics")
        .stockQuantity(15)
        .build();

    restTemplate.postForEntity(
        "http://localhost:" + port + "/api/v1/products",
        request1,
        ProductResponse.class
    );

    restTemplate.postForEntity(
        "http://localhost:" + port + "/api/v1/products",
        request2,
        ProductResponse.class
    );

    // Act
    ResponseEntity<List<ProductResponse>> response = restTemplate.exchange(
        "http://localhost:" + port + "/api/v1/products",
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<List<ProductResponse>>() {}
    );

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertTrue(response.getBody().size() >= 2);
  }

  @Test
  @DisplayName("Should update a product successfully - Integration Test")
  void givenValidUpdateRequestWhenUpdateProductThenReturnUpdatedProductIntegration() {
    // Arrange - First create a product
    CreateProductRequest createRequest = CreateProductRequest.builder()
        .name("Tablet")
        .description("Android tablet")
        .price(new BigDecimal("199.99"))
        .category("Electronics")
        .stockQuantity(20)
        .build();

    ResponseEntity<ProductResponse> createResponse = restTemplate.postForEntity(
        "http://localhost:" + port + "/api/v1/products",
        createRequest,
        ProductResponse.class
    );

    Long productId = createResponse.getBody().getId();

    UpdateProductRequest updateRequest = UpdateProductRequest.builder()
        .name("Updated Tablet")
        .description("Updated Android tablet")
        .price(new BigDecimal("249.99"))
        .category("Electronics")
        .stockQuantity(25)
        .active(true)
        .build();

    // Act
    ResponseEntity<ProductResponse> response = restTemplate.exchange(
        "http://localhost:" + port + "/api/v1/products/" + productId,
        HttpMethod.PUT,
        new HttpEntity<>(updateRequest),
        ProductResponse.class
    );

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(productId, response.getBody().getId());
    assertEquals("Updated Tablet", response.getBody().getName());
    assertEquals("Updated Android tablet", response.getBody().getDescription());
    assertEquals(new BigDecimal("249.99"), response.getBody().getPrice());
    assertEquals("Electronics", response.getBody().getCategory());
    assertEquals(25, response.getBody().getStockQuantity());
    assertTrue(response.getBody().getActive());
  }

  @Test
  @DisplayName("Should delete a product successfully - Integration Test")
  void givenValidProductIdWhenDeleteProductThenReturnNoContentIntegration() {
    // Arrange - First create a product
    CreateProductRequest createRequest = CreateProductRequest.builder()
        .name("Headphones")
        .description("Wireless headphones")
        .price(new BigDecimal("149.99"))
        .category("Electronics")
        .stockQuantity(30)
        .build();

    ResponseEntity<ProductResponse> createResponse = restTemplate.postForEntity(
        "http://localhost:" + port + "/api/v1/products",
        createRequest,
        ProductResponse.class
    );

    Long productId = createResponse.getBody().getId();

    // Act
    ResponseEntity<Void> response = restTemplate.exchange(
        "http://localhost:" + port + "/api/v1/products/" + productId,
        HttpMethod.DELETE,
        null,
        Void.class
    );

    // Assert
    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

    // Verify product is deleted
    ResponseEntity<String> getResponse = restTemplate.getForEntity(
        "http://localhost:" + port + "/api/v1/products/" + productId,
        String.class
    );
    assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode());
  }

  @Test
  @DisplayName("Should return validation error when product name is blank - Integration Test")
  void givenBlankProductNameWhenCreateProductThenReturnValidationErrorIntegration() {
    // Arrange
    CreateProductRequest request = CreateProductRequest.builder()
        .name("")
        .description("Test product")
        .price(new BigDecimal("99.99"))
        .category("Electronics")
        .stockQuantity(10)
        .build();

    // Act
    ResponseEntity<String> response = restTemplate.postForEntity(
        "http://localhost:" + port + "/api/v1/products",
        request,
        String.class
    );

    // Assert
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertTrue(response.getBody().contains("VALIDATION_ERROR"));
  }

  @Test
  @DisplayName("Should return 404 when product not found - Integration Test")
  void givenInvalidProductIdWhenGetProductThenReturnNotFoundIntegration() {
    // Act
    ResponseEntity<String> response = restTemplate.getForEntity(
        "http://localhost:" + port + "/api/v1/products/999999",
        String.class
    );

    // Assert
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertTrue(response.getBody().contains("PRODUCT_NOT_FOUND"));
  }
}
