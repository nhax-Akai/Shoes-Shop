package com.shoesshop.services;

import com.shoesshop.commons.CartConstant;
import com.shoesshop.models.Account;
import com.shoesshop.models.Cart;
import com.shoesshop.repositories.CartRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepos cartRepos;

    public List<Cart> getList(){
        return  cartRepos.findAll();
    }

    public Cart findCartIsOrdering(Account account){
        List<Cart> cartList = cartRepos.findAllByAccount(account);
        if(cartList.isEmpty()){
            return new Cart();
        }
        for(Cart cart:cartList){
            if(cart.getStatus().equals(CartConstant.ORDERING)){
                return cart;
            }
        }
        return new Cart();
    }

    public List<Cart> findAllCartIsOrdered(Account account){
        List<Cart> cartList = cartRepos.findAllByAccount(account);
        List<Cart> listCartOrdered = new ArrayList<>();
        if(cartList.isEmpty()){
            return null;
        }
        for(Cart cart: cartList){
            if(cart.getStatus().equals(CartConstant.ORDERED)){
                listCartOrdered.add(cart);
            }
        }
        return listCartOrdered;
    }

    public void save(Cart cart){
        cartRepos.save(cart);
    }
}
