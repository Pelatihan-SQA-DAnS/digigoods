package com.example.digigoods.controller;

import com.example.digigoods.dto.CreateProductRequest;
import com.example.digigoods.dto.ProductResponse;
import com.example.digigoods.dto.UpdateProductRequest;
import com.example.digigoods.service.ProductService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing products.
 */
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

  private final ProductService productService;

  @Autowired
  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  /**
   * Creates a new product.
   *
   * @param request the product creation request
   * @return the created product response
   */
  @PostMapping
  public ResponseEntity<ProductResponse> createProduct(
      @Valid @RequestBody CreateProductRequest request) {
    ProductResponse response = productService.createProduct(request);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  /**
   * Retrieves a product by its ID.
   *
   * @param id the product ID
   * @return the product response
   */
  @GetMapping("/{id}")
  public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
    ProductResponse response = productService.getProductById(id);
    return ResponseEntity.ok(response);
  }

  /**
   * Retrieves all products or products by category.
   *
   * @param category optional category filter
   * @return list of product responses
   */
  @GetMapping
  public ResponseEntity<List<ProductResponse>> getAllProducts(
      @RequestParam(required = false) String category) {
    List<ProductResponse> responses;
    if (category != null && !category.trim().isEmpty()) {
      responses = productService.getProductsByCategory(category);
    } else {
      responses = productService.getAllProducts();
    }
    return ResponseEntity.ok(responses);
  }

  /**
   * Updates an existing product.
   *
   * @param id the product ID
   * @param request the product update request
   * @return the updated product response
   */
  @PutMapping("/{id}")
  public ResponseEntity<ProductResponse> updateProduct(
      @PathVariable Long id,
      @Valid @RequestBody UpdateProductRequest request) {
    ProductResponse response = productService.updateProduct(id, request);
    return ResponseEntity.ok(response);
  }

  /**
   * Deletes a product by its ID.
   *
   * @param id the product ID
   * @return no content response
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
    productService.deleteProduct(id);
    return ResponseEntity.noContent().build();
  }
}
