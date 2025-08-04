package com.example.digigoods.repository;

import com.example.digigoods.model.Product;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

/**
 * In-memory implementation of ProductRepository.
 */
@Repository
public class InMemoryProductRepository implements ProductRepository {

  private final ConcurrentHashMap<Long, Product> products = new ConcurrentHashMap<>();
  private final AtomicLong idGenerator = new AtomicLong(1);

  @Override
  public Product save(Product product) {
    if (product.getId() == null) {
      product.setId(idGenerator.getAndIncrement());
    }
    products.put(product.getId(), product);
    return product;
  }

  @Override
  public Optional<Product> findById(Long id) {
    return Optional.ofNullable(products.get(id));
  }

  @Override
  public List<Product> findAll() {
    return products.values().stream().collect(Collectors.toList());
  }

  @Override
  public List<Product> findByCategory(String category) {
    return products.values().stream()
        .filter(product -> category.equals(product.getCategory()))
        .collect(Collectors.toList());
  }

  @Override
  public void deleteById(Long id) {
    products.remove(id);
  }

  @Override
  public boolean existsById(Long id) {
    return products.containsKey(id);
  }
}
