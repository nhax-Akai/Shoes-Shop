package com.shoesshop.controllers;


import com.shoesshop.models.Account;
import com.shoesshop.models.Cart;
import com.shoesshop.services.AccountService;
import com.shoesshop.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/history")
public class CheckoutController {
    @Autowired
    AccountService accountService;
    @Autowired
    CartService cartService;

    @GetMapping("")
    public String getHistory(@CookieValue(name = "email", defaultValue = "") String email,
                             Model model){
        Optional<Account> accountSaved = accountService.getAccountByEmail(email);
        if(!Objects.equals(email, "")){
            if(accountSaved.isPresent()){
                model.addAttribute("account", accountSaved.get());
            }else{
                model.addAttribute("account", null);
            }
        }
        List<Cart> orderedCartList = cartService.findAllCartIsOrdered(accountSaved.get());
        model.addAttribute("receipts", orderedCartList);

        return "home/history";
    }
}
