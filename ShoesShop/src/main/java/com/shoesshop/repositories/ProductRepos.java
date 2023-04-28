package com.shoesshop.repositories;

import com.shoesshop.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepos extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.name LIKE %?1%"
            + " OR p.description LIKE %?1%"
            + " OR CONCAT(p.price, '') LIKE %?1%")
    Page<Product> searchProduct(String keyword, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.name LIKE %?1%")
    List<Product> searchProductByKeyword(String keyword);
}
