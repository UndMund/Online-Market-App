package org.example.dao;

import org.example.entity.Category;
import org.example.entity.Product;
import org.example.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByCategoryAndStatus(Category category, Status status);

    List<Product> findAllByStatus(Status status);

    Optional<Product> findByProductNameAndUserId(String productName, Long userId);
}
