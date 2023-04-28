package com.shoesshop.services;

import com.shoesshop.models.User;
import com.shoesshop.repositories.UserRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepos userRepos;

    public void save(User user){
        userRepos.save(user);
    }
}
