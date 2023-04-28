package com.shoesshop.controllers;

import com.shoesshop.commons.CartConstant;
import com.shoesshop.models.*;
import com.shoesshop.repositories.CartItemRepos;
import com.shoesshop.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("cart")
public class CartController {
    @Autowired
    AccountService accountService;
    @Autowired
    CartService cartService;
    @Autowired
    ProductService productService;
    @Autowired
    CartItemService cartItemService;
    @Autowired
    ReceiptService receiptService;


    @GetMapping("")
    public String getCart(@CookieValue(name = "email", defaultValue = "") String email,
                          Model model){
        Optional<Account> accountSaved = accountService.getAccountByEmail(email);

        if(!Objects.equals(email, "")){
            if(accountSaved.isPresent()){
                model.addAttribute("account", accountSaved.get());
            }else{
                model.addAttribute("account", null);
            }
        }else{
            return "redirect:/user/login";
        }

        Cart cart = cartService.findCartIsOrdering(accountSaved.get());
        model.addAttribute("cart", cart);
        model.addAttribute("userInfor", accountSaved.get().getUser());
        return "home/checkout";
    }

    @PostMapping("")
    public String postCartItem(@RequestParam(name = "product") Long id,
                               @RequestParam(name = "quantity") int quantity,
                               @CookieValue(name = "email", defaultValue = "") String email){
        if(Objects.equals(email, "")){
            return "redirect:/user/login";
        }
        Optional<Account> accountSaved = accountService.getAccountByEmail(email);
        Cart cart = cartService.findCartIsOrdering(accountSaved.get());
        Optional<Product> product = productService.getById(id);


        boolean isHave = false;
        CartItem cartItemHave = null;
        double total = 0;
        if(cart.getCartItems() != null){
            for(CartItem cartItem: cart.getCartItems()){
                if(cartItem.getProduct().equals(product.get())){
                    isHave = true;
                    cartItemHave = cartItem;
                }
            }
        }
        if(!isHave){
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product.get());
            cartItem.setCart(cart);
            cartItem.setQuantity(quantity);
            cartItemService.save(cartItem);
            total += cartItem.getProduct().getPrice() * quantity;
        }else{
            cartItemHave.setQuantity(cartItemHave.getQuantity() + quantity);
            cartItemService.save(cartItemHave);
        }

        if(cart.getCartItems() != null){
            for(CartItem item: cart.getCartItems()){
                total += item.getProduct().getPrice() * item.getQuantity();
            }
        }

        cart.setTotal(total);
        cart.setAccount(accountSaved.get());
        cartService.save(cart);

        return "redirect:/cart";
    }
    @GetMapping("delete/{id}")
    public String removeItem(@PathVariable Long id,
                             @CookieValue(name = "email", defaultValue = "") String email){
        if(Objects.equals(email, "")){
            return "redirect:/user/login";
        }
        Optional<CartItem> cartItem = cartItemService.findById(id);
        if(cartItem.isPresent()){
            cartItem.get().setCart(null);
            cartItem.get().setProduct(null);

            cartItemService.delete(cartItem.get());
        }
        Optional<Account> accountSaved = accountService.getAccountByEmail(email);
        Cart cart = cartService.findCartIsOrdering(accountSaved.get());

        double total = 0;
        if(cart.getCartItems() != null){
            for(CartItem item: cart.getCartItems()){
                total += item.getProduct().getPrice() * item.getQuantity();
            }
        }
        cart.setTotal(total);
        cart.setAccount(accountSaved.get());
        cartService.save(cart);

        return "redirect:/cart";
    }
    @PostMapping("checkout")
    public String postCheckout(@CookieValue(name = "email", defaultValue = "") String email,
                               @RequestParam(name = "fullName") String fullName,
                               @RequestParam(name = "phoneNumber") String phoneNumber,
                               @RequestParam(name = "address") String address){
        if(Objects.equals(email, "")){
            return "redirect:/user/login";
        }
        Optional<Account> accountSaved = accountService.getAccountByEmail(email);

        Cart cart = cartService.findCartIsOrdering(accountSaved.get());
        cart.setStatus(CartConstant.ORDERED);
        cartService.save(cart);

        Receipt receipt = new Receipt();
        receipt.setCart(cart);
        receipt.setAddress(address);
        receipt.setFullName(fullName);
        receipt.setPhoneNumber(phoneNumber);
        receiptService.save(receipt);


        return "redirect:/history";
    }

}
