package com.shoesshop.services;

import com.shoesshop.models.Category;
import com.shoesshop.repositories.CategoryRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepos categoryRepos;

    public List<Category> getAllList(){
        return categoryRepos.findAll();
    }
    public Optional<Category> getById(Long id){
        return categoryRepos.findById(id);
    }
    public void save(Category category){
        categoryRepos.save(category);
    }
    public void delete(Long id){
        categoryRepos.deleteById(id);
    }

}
