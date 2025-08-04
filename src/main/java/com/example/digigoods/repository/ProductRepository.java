package com.example.digigoods.repository;

import com.example.digigoods.model.Product;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing products.
 */
public interface ProductRepository {

  /**
   * Saves a product.
   *
   * @param product the product to save
   * @return the saved product
   */
  Product save(Product product);

  /**
   * Finds a product by its ID.
   *
   * @param id the product ID
   * @return the product if found
   */
  Optional<Product> findById(Long id);

  /**
   * Finds all products.
   *
   * @return list of all products
   */
  List<Product> findAll();

  /**
   * Finds products by category.
   *
   * @param category the product category
   * @return list of products in the category
   */
  List<Product> findByCategory(String category);

  /**
   * Deletes a product by its ID.
   *
   * @param id the product ID
   */
  void deleteById(Long id);

  /**
   * Checks if a product exists by its ID.
   *
   * @param id the product ID
   * @return true if the product exists
   */
  boolean existsById(Long id);
}
