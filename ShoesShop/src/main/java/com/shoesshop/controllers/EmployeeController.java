package com.shoesshop.controllers;

import com.shoesshop.models.Receipt;
import com.shoesshop.services.AccountService;
import com.shoesshop.services.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.shoesshop.utils.Time.getPresentTime;

@Controller
@RequestMapping("employee")
public class EmployeeController {
    @Autowired
    private ReceiptService receiptService;

    @Autowired
    private AccountService accountService;
    @GetMapping("")
    public String getIndex(Model model,
                           @CookieValue(name = "email", defaultValue = "") String email){
        if(!Objects.equals("email","")){
            if(Objects.equals(accountService.getAccountByEmail(email).get().getRole(), "ROLE_EMPLOYEE")){
                model.addAttribute("receipts", receiptService.getOrder());
                return "employee/index";
            }
        }

        return "redirect:/";
    }
    @PostMapping("/delivered/{id}")
    public String postDelivered(@PathVariable Long id){
        Receipt receipt = receiptService.getById(id).get();
        receipt.setUpdateAt(getPresentTime());
        receipt.setDelivered(true);
        receiptService.save(receipt);
        return "redirect:/employee";
    }
    @GetMapping("history")
    public String getHistory(Model model){
        model.addAttribute("receipts", receiptService.getOrderedToday()
                .stream()
                .sorted(Comparator.comparing(Receipt::getUpdateAt).reversed()).collect(Collectors.toList()));
        return "employee/history";
    }
}
