package com.shoesshop.repositories;

import com.shoesshop.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepos extends JpaRepository<CartItem, Long> {
}
