package org.example.repository;

import org.example.entity.Product;
import org.example.entity.Status;
import org.example.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, QuerydslPredicateExecutor<Product> {
    List<Product> findAllByStatus(Status status);
    Slice<Product> findAllByStatus(Status status, Pageable pageable);
    Optional<Product> findByProductNameAndUserId(String productName, Long userId);
    List<Product> findAllByUserAndStatus(User user, Status status);
}
