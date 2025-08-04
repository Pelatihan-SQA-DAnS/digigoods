package com.example.digigoods.service;

import com.example.digigoods.dto.CreateProductRequest;
import com.example.digigoods.dto.ProductResponse;
import com.example.digigoods.dto.UpdateProductRequest;
import java.util.List;

/**
 * Service interface for managing products.
 */
public interface ProductService {

  /**
   * Creates a new product.
   *
   * @param request the product creation request
   * @return the created product response
   */
  ProductResponse createProduct(CreateProductRequest request);

  /**
   * Retrieves a product by its ID.
   *
   * @param id the product ID
   * @return the product response
   */
  ProductResponse getProductById(Long id);

  /**
   * Retrieves all products.
   *
   * @return list of all product responses
   */
  List<ProductResponse> getAllProducts();

  /**
   * Retrieves products by category.
   *
   * @param category the product category
   * @return list of product responses in the category
   */
  List<ProductResponse> getProductsByCategory(String category);

  /**
   * Updates an existing product.
   *
   * @param id the product ID
   * @param request the product update request
   * @return the updated product response
   */
  ProductResponse updateProduct(Long id, UpdateProductRequest request);

  /**
   * Deletes a product by its ID.
   *
   * @param id the product ID
   */
  void deleteProduct(Long id);
}
