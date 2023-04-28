package com.shoesshop.controllers;

import com.shoesshop.models.Account;
import com.shoesshop.models.User;
import com.shoesshop.services.AccountService;
import com.shoesshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("user")
public class AccountController {

    @Autowired()
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;


    @GetMapping("register")
    public String getRegister(Model model){
        Account account = new Account();
        model.addAttribute(account);
        return "account/register";
    }
    @PostMapping("register")
    public String postRegister(@ModelAttribute("account") Account account,
                               Model model,
                               @RequestParam(name = "re-password") String repass){
        String error = "";
        if(account.getEmail().length() == 0 || account.getPassword().length() == 0 || account.getPhoneNumber().length() == 0){
            error = "Please fill full of form";
            model.addAttribute("error",error);
            return "account/register";
        }
        if(accountService.isEmailUsed(account.getEmail())){
            error = "Email was used. Please use another email!";
            model.addAttribute("error",error);
            return "account/register";
        }
        if(account.getPassword().length() < 8){
            error = "Password length have less 8 character!";
            model.addAttribute("error",error);
            return "account/register";
        }
        if(!Objects.equals(account.getPassword(), repass)){
            error = "Password is not match!";
            model.addAttribute("error",error);
            return "account/register";
        }
        String encryptPwd = passwordEncoder.encode(account.getPassword());
        account.setPassword(encryptPwd);
        accountService.saveAccount(account);

        String message = "Register Account Successfully";
        model.addAttribute("message", message);
        return "account/login";
    }

    @GetMapping("login")
    public String getLogin(Model model){
        Account account = new Account();
        model.addAttribute("account", account);
        return "account/login";
    }


    @PostMapping("login")
    public String postLogin(@ModelAttribute( name = "account") Account account,
                            Model model,
                            HttpServletResponse response){
        String error = "";
        if(!accountService.isEmailUsed(account.getEmail())){
            error = "Email or password is not correct !";
            model.addAttribute("error", error);
            return "account/login";
        }else{
            if(passwordEncoder.matches(account.getPassword(), accountService.getAccountByEmail(account.getEmail()).get().getPassword())){
                Cookie userCookie = new Cookie("email", account.getEmail());
                userCookie.setMaxAge(60*60*24);
                userCookie.setPath("/");
                response.addCookie(userCookie);

                if(accountService.getAccountByEmail(account.getEmail()).get().getUser() != null){
                    return "redirect:/";
                }else{
                    return "redirect:/user/confirm";
                }
            }else{
                error = "Email or password is not correct !";
                model.addAttribute("error", error);
            }
        }
        return "account/login";
    }
    @GetMapping("profile")
    public String getProfile(@CookieValue(value = "email" , defaultValue = "") String email,
                             Model model){
        Optional<Account> account = accountService.getAccountByEmail(email);
        if(account.isPresent()){
            model.addAttribute("userInfo",account.get().getUser());
        }
        return "account/profile";
    }
    @PostMapping("profile")
    public String postProfile(@ModelAttribute("user") User user,
                              Model model,
                              @CookieValue(value = "email" , defaultValue = "") String email){
        Optional<Account> account = accountService.getAccountByEmail(email);
        account.get().setUser(user);
        accountService.saveAccount(account.get());
        userService.save(user);
        model.addAttribute("message", "Update information successfully");
        return "redirect:/";
    }
    @GetMapping("confirm")
    public String getConfirm(){
        return "account/confirm";
    }
    @PostMapping("confirm")
    public String postConfirm(@ModelAttribute("user") User user,
                              Model model){
        userService.save(user);
        model.addAttribute("message", "Update information successfully");
        return "account/profile";
    }
}
