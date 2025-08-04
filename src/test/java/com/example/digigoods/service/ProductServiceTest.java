package com.example.digigoods.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.digigoods.dto.CreateProductRequest;
import com.example.digigoods.dto.ProductResponse;
import com.example.digigoods.dto.UpdateProductRequest;
import com.example.digigoods.exception.ProductNotFoundException;
import com.example.digigoods.model.Product;
import com.example.digigoods.repository.ProductRepository;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

  @Mock
  private ProductRepository productRepository;

  private ProductService productService;

  @BeforeEach
  void setUp() {
    productService = new ProductServiceImpl(productRepository);
  }

  @Test
  @DisplayName("Should create a product successfully")
  void givenValidCreateProductRequestWhenCreateProductThenReturnProductResponse() {
    // Arrange
    CreateProductRequest request = CreateProductRequest.builder()
        .name("Laptop")
        .description("High-performance laptop")
        .price(new BigDecimal("999.99"))
        .category("Electronics")
        .stockQuantity(10)
        .build();

    Product savedProduct = Product.builder()
        .id(1L)
        .name("Laptop")
        .description("High-performance laptop")
        .price(new BigDecimal("999.99"))
        .category("Electronics")
        .stockQuantity(10)
        .active(true)
        .build();

    when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

    // Act
    ProductResponse response = productService.createProduct(request);

    // Assert
    assertNotNull(response);
    assertEquals(1L, response.getId());
    assertEquals("Laptop", response.getName());
    assertEquals("High-performance laptop", response.getDescription());
    assertEquals(new BigDecimal("999.99"), response.getPrice());
    assertEquals("Electronics", response.getCategory());
    assertEquals(10, response.getStockQuantity());
    assertEquals(true, response.getActive());
    verify(productRepository).save(any(Product.class));
  }

  @Test
  @DisplayName("Should retrieve a product by ID successfully")
  void givenValidProductIdWhenGetProductByIdThenReturnProductResponse() {
    // Arrange
    Long productId = 1L;
    Product product = Product.builder()
        .id(productId)
        .name("Laptop")
        .description("High-performance laptop")
        .price(new BigDecimal("999.99"))
        .category("Electronics")
        .stockQuantity(10)
        .active(true)
        .build();

    when(productRepository.findById(productId)).thenReturn(Optional.of(product));

    // Act
    ProductResponse response = productService.getProductById(productId);

    // Assert
    assertNotNull(response);
    assertEquals(productId, response.getId());
    assertEquals("Laptop", response.getName());
    assertEquals("High-performance laptop", response.getDescription());
    assertEquals(new BigDecimal("999.99"), response.getPrice());
    assertEquals("Electronics", response.getCategory());
    assertEquals(10, response.getStockQuantity());
    assertEquals(true, response.getActive());
    verify(productRepository).findById(productId);
  }

  @Test
  @DisplayName("Should throw ProductNotFoundException when product not found by ID")
  void givenInvalidProductIdWhenGetProductByIdThenThrowProductNotFoundException() {
    // Arrange
    Long productId = 999L;
    when(productRepository.findById(productId)).thenReturn(Optional.empty());

    // Act & Assert
    ProductNotFoundException exception = assertThrows(ProductNotFoundException.class,
        () -> productService.getProductById(productId));
    assertEquals("Product not found with id: " + productId, exception.getMessage());
    verify(productRepository).findById(productId);
  }

  @Test
  @DisplayName("Should retrieve all products successfully")
  void givenProductsExistWhenGetAllProductsThenReturnProductResponseList() {
    // Arrange
    List<Product> products = Arrays.asList(
        Product.builder()
            .id(1L)
            .name("Laptop")
            .description("High-performance laptop")
            .price(new BigDecimal("999.99"))
            .category("Electronics")
            .stockQuantity(10)
            .active(true)
            .build(),
        Product.builder()
            .id(2L)
            .name("Mouse")
            .description("Wireless mouse")
            .price(new BigDecimal("29.99"))
            .category("Electronics")
            .stockQuantity(50)
            .active(true)
            .build()
    );

    when(productRepository.findAll()).thenReturn(products);

    // Act
    List<ProductResponse> responses = productService.getAllProducts();

    // Assert
    assertNotNull(responses);
    assertEquals(2, responses.size());
    assertEquals("Laptop", responses.get(0).getName());
    assertEquals("Mouse", responses.get(1).getName());
    verify(productRepository).findAll();
  }

  @Test
  @DisplayName("Should retrieve products by category successfully")
  void givenValidCategoryWhenGetProductsByCategoryThenReturnProductResponseList() {
    // Arrange
    String category = "Electronics";
    List<Product> products = Arrays.asList(
        Product.builder()
            .id(1L)
            .name("Laptop")
            .description("High-performance laptop")
            .price(new BigDecimal("999.99"))
            .category("Electronics")
            .stockQuantity(10)
            .active(true)
            .build()
    );

    when(productRepository.findByCategory(category)).thenReturn(products);

    // Act
    List<ProductResponse> responses = productService.getProductsByCategory(category);

    // Assert
    assertNotNull(responses);
    assertEquals(1, responses.size());
    assertEquals("Laptop", responses.get(0).getName());
    assertEquals("Electronics", responses.get(0).getCategory());
    verify(productRepository).findByCategory(category);
  }

  @Test
  @DisplayName("Should update a product successfully")
  void givenValidUpdateRequestWhenUpdateProductThenReturnUpdatedProductResponse() {
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

    Product existingProduct = Product.builder()
        .id(productId)
        .name("Laptop")
        .description("High-performance laptop")
        .price(new BigDecimal("999.99"))
        .category("Electronics")
        .stockQuantity(10)
        .active(true)
        .build();

    Product updatedProduct = Product.builder()
        .id(productId)
        .name("Updated Laptop")
        .description("Updated high-performance laptop")
        .price(new BigDecimal("1099.99"))
        .category("Electronics")
        .stockQuantity(15)
        .active(true)
        .build();

    when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
    when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

    // Act
    ProductResponse response = productService.updateProduct(productId, request);

    // Assert
    assertNotNull(response);
    assertEquals(productId, response.getId());
    assertEquals("Updated Laptop", response.getName());
    assertEquals("Updated high-performance laptop", response.getDescription());
    assertEquals(new BigDecimal("1099.99"), response.getPrice());
    assertEquals("Electronics", response.getCategory());
    assertEquals(15, response.getStockQuantity());
    assertEquals(true, response.getActive());
    verify(productRepository).findById(productId);
    verify(productRepository).save(any(Product.class));
  }

  @Test
  @DisplayName("Should throw ProductNotFoundException when updating non-existent product")
  void givenInvalidProductIdWhenUpdateProductThenThrowProductNotFoundException() {
    // Arrange
    Long productId = 999L;
    UpdateProductRequest request = UpdateProductRequest.builder()
        .name("Updated Laptop")
        .build();

    when(productRepository.findById(productId)).thenReturn(Optional.empty());

    // Act & Assert
    ProductNotFoundException exception = assertThrows(ProductNotFoundException.class,
        () -> productService.updateProduct(productId, request));
    assertEquals("Product not found with id: " + productId, exception.getMessage());
    verify(productRepository).findById(productId);
  }

  @Test
  @DisplayName("Should delete a product successfully")
  void givenValidProductIdWhenDeleteProductThenDeleteProduct() {
    // Arrange
    Long productId = 1L;
    when(productRepository.existsById(productId)).thenReturn(true);

    // Act
    productService.deleteProduct(productId);

    // Assert
    verify(productRepository).existsById(productId);
    verify(productRepository).deleteById(productId);
  }

  @Test
  @DisplayName("Should throw ProductNotFoundException when deleting non-existent product")
  void givenInvalidProductIdWhenDeleteProductThenThrowProductNotFoundException() {
    // Arrange
    Long productId = 999L;
    when(productRepository.existsById(productId)).thenReturn(false);

    // Act & Assert
    ProductNotFoundException exception = assertThrows(ProductNotFoundException.class,
        () -> productService.deleteProduct(productId));
    assertEquals("Product not found with id: " + productId, exception.getMessage());
    verify(productRepository).existsById(productId);
  }
}
