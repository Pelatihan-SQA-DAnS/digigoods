package com.example.digigoods.service;

import com.example.digigoods.dto.CreateProductRequest;
import com.example.digigoods.dto.ProductResponse;
import com.example.digigoods.dto.UpdateProductRequest;
import com.example.digigoods.exception.ProductNotFoundException;
import com.example.digigoods.model.Product;
import com.example.digigoods.repository.ProductRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of ProductService for managing products.
 */
@Service
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;

  @Autowired
  public ProductServiceImpl(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public ProductResponse createProduct(CreateProductRequest request) {
    Product product = Product.builder()
        .name(request.getName())
        .description(request.getDescription())
        .price(request.getPrice())
        .category(request.getCategory())
        .stockQuantity(request.getStockQuantity())
        .active(true)
        .build();

    Product savedProduct = productRepository.save(product);

    return mapToProductResponse(savedProduct);
  }

  @Override
  public ProductResponse getProductById(Long id) {
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));

    return mapToProductResponse(product);
  }

  @Override
  public List<ProductResponse> getAllProducts() {
    List<Product> products = productRepository.findAll();
    return products.stream()
        .map(this::mapToProductResponse)
        .collect(Collectors.toList());
  }

  @Override
  public List<ProductResponse> getProductsByCategory(String category) {
    List<Product> products = productRepository.findByCategory(category);
    return products.stream()
        .map(this::mapToProductResponse)
        .collect(Collectors.toList());
  }

  @Override
  public ProductResponse updateProduct(Long id, UpdateProductRequest request) {
    Product existingProduct = productRepository.findById(id)
        .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));

    // Update only non-null fields
    if (request.getName() != null) {
      existingProduct.setName(request.getName());
    }
    if (request.getDescription() != null) {
      existingProduct.setDescription(request.getDescription());
    }
    if (request.getPrice() != null) {
      existingProduct.setPrice(request.getPrice());
    }
    if (request.getCategory() != null) {
      existingProduct.setCategory(request.getCategory());
    }
    if (request.getStockQuantity() != null) {
      existingProduct.setStockQuantity(request.getStockQuantity());
    }
    if (request.getActive() != null) {
      existingProduct.setActive(request.getActive());
    }

    Product updatedProduct = productRepository.save(existingProduct);
    return mapToProductResponse(updatedProduct);
  }

  @Override
  public void deleteProduct(Long id) {
    if (!productRepository.existsById(id)) {
      throw new ProductNotFoundException("Product not found with id: " + id);
    }
    productRepository.deleteById(id);
  }

  private ProductResponse mapToProductResponse(Product product) {
    return ProductResponse.builder()
        .id(product.getId())
        .name(product.getName())
        .description(product.getDescription())
        .price(product.getPrice())
        .category(product.getCategory())
        .stockQuantity(product.getStockQuantity())
        .active(product.getActive())
        .build();
  }
}
