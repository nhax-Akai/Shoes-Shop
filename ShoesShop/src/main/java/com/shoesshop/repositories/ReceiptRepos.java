package com.shoesshop.repositories;

import com.shoesshop.models.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiptRepos extends JpaRepository<Receipt, Long> {
}
