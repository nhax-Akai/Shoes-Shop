package com.shoesshop.services;

import com.shoesshop.models.CartItem;
import com.shoesshop.repositories.CartItemRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartItemService {
    @Autowired
    private CartItemRepos cartItemRepos;

    public Optional<CartItem> findById(Long id){
        return cartItemRepos.findById(id);
    }

    public void save(CartItem cartItem){
        cartItemRepos.save(cartItem);
    }

    public void deleteById(Long id){
        cartItemRepos.deleteById(id);
    }

    public void delete(CartItem cartItem){
        cartItemRepos.delete(cartItem);
    }
}
