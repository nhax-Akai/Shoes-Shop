package com.shoesshop.services;

import com.shoesshop.models.Account;
import com.shoesshop.repositories.AccountRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepos accountRepos;

    public List<Account> getAccountList(){
        return accountRepos.findAll();
    }
    public Optional<Account> getAccountById(Long id){
        return accountRepos.findById(id);
    }
    public boolean isEmailUsed(String email){
        return accountRepos.findByEmail(email).isPresent();
    }
    public Optional<Account> getAccountByEmail(String email) {
        return accountRepos.findByEmail(email);
    }
    public void saveAccount(Account account){
        accountRepos.save(account);
    }

    public List<Account> search(String keyword){
        return accountRepos.searchAccount(keyword);
    }
}
