package com.shoesshop.repositories;

import com.shoesshop.models.Account;
import com.shoesshop.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepos extends JpaRepository<Cart, Long> {
    List<Cart> findAllByAccount(Account account);
}
