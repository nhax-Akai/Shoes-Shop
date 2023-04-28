package com.shoesshop.repositories;

import com.shoesshop.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepos extends JpaRepository<Category, Long> {
}
