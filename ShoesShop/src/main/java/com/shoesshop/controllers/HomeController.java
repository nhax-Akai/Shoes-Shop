package com.shoesshop.controllers;

import com.shoesshop.models.Account;
import com.shoesshop.models.Product;
import com.shoesshop.services.AccountService;
import com.shoesshop.services.CategoryService;
import com.shoesshop.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("")
public class HomeController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;


    @GetMapping("")
    public String getHome(@CookieValue(value = "email", defaultValue = "") String email,
                          Model model){
        Optional<Account> account = accountService.getAccountByEmail(email);

        if(!Objects.equals(email, "")){
            if(account.isPresent()){
                model.addAttribute("account", account.get());
                if(Objects.equals(account.get().getRole(),"ROLE_ADMIN")){
                    return "redirect:/admin";
                }
                if(Objects.equals(account.get().getRole(),"ROLE_EMPLOYEE")){
                    return "redirect:/employee";
                }
            }else{
                model.addAttribute("account", null);
            }
        }

        return "home/index";
    }
    @GetMapping("product")
    public String getProduct(Model model,
                             @CookieValue(value = "email", defaultValue = "") String email){
        Optional<Account> account = accountService.getAccountByEmail(email);

        if(!Objects.equals(email, "")){
            if(account.isPresent()){
                model.addAttribute("account", account.get());
            }else{
                model.addAttribute("account", null);
            }
        }
        model.addAttribute("categories", categoryService.getAllList());
        model.addAttribute("products", productService.getAllList());
        return "home/shop";
    }


    @GetMapping("view")
    public String getView(@RequestParam Long id,
                          Model model,
                          @CookieValue(value = "email", defaultValue = "") String email){
        Optional<Account> account = accountService.getAccountByEmail(email);

        if(!Objects.equals(email, "")){
            if(account.isPresent()){
                model.addAttribute("account", account.get());
            }else{
                model.addAttribute("account", null);
            }
        }
        Optional<Product> product = productService.getById(id);
        product.ifPresent(value -> model.addAttribute("thisProduct", value));
        return "home/single";
    }
    @GetMapping("search")
    public String getSearch(@RequestParam(name = "keyword") String keyword,
                            Model model,
                            @CookieValue(value = "email", defaultValue = "") String email){
        Optional<Account> account = accountService.getAccountByEmail(email);

        if(!Objects.equals(email, "")){
            if(account.isPresent()){
                model.addAttribute("account", account.get());
            }else{
                model.addAttribute("account", null);
            }
        }
        model.addAttribute("categories", categoryService.getAllList());
        model.addAttribute("products", productService.searchProductByKeyword(keyword));
        model.addAttribute("keyword", keyword);
        return "home/shop";
    }
    @GetMapping("contact")
    public String getContact(@CookieValue(value = "email", defaultValue = "") String email,
                             Model model){
        Optional<Account> account = accountService.getAccountByEmail(email);

        if(!Objects.equals(email, "")){
            if(account.isPresent()){
                model.addAttribute("account", account.get());
            }else{
                model.addAttribute("account", null);
            }
        }
        return "home/contact";
    }
    @GetMapping("about")
    public String getAbout(@CookieValue(value = "email", defaultValue = "") String email,
                           Model model){
        Optional<Account> account = accountService.getAccountByEmail(email);

        if(!Objects.equals(email, "")){
            if(account.isPresent()){
                model.addAttribute("account", account.get());
            }else{
                model.addAttribute("account", null);
            }
        }
        return "home/about";
    }
    @GetMapping("404")
    public String getError(@CookieValue(value = "email", defaultValue = "") String email, Model model){
        Optional<Account> account = accountService.getAccountByEmail(email);

        if(!Objects.equals(email, "")){
            if(account.isPresent()){
                model.addAttribute("account", account.get());
            }else{
                model.addAttribute("account", null);
            }
        }
        return "home/404";
    }
}
