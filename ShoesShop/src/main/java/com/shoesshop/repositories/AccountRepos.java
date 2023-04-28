package com.shoesshop.repositories;

import com.shoesshop.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface AccountRepos extends JpaRepository<Account, Long> {
    Optional<Account> findByEmail(String email);

    @Query("SELECT a FROM Account a WHERE a.email LIKE %?1%"
            + " OR a.role LIKE %?1%")
    List<Account> searchAccount(String keyword);
}
