package com.shoesshop.repositories;

import com.shoesshop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepos extends JpaRepository<User, Long> {
}
