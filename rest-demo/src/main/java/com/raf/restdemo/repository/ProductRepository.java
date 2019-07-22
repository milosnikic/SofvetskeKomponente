package com.raf.restdemo.repository;

import com.raf.restdemo.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByTitle(String title);

}
