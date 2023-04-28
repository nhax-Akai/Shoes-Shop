package com.shoesshop.services;

import com.shoesshop.models.Product;
import com.shoesshop.repositories.ProductRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepos productRepos;

    public List<Product> getAllList(){
        return productRepos.findAll();
    }
    public Optional<Product> getById(Long id){
        return productRepos.findById(id);
    }
    public void save(Product product){
        productRepos.save(product);
    }
    public void delete(Product product){
        productRepos.delete(product);
    }

    public Page<Product> pageProductsandSort(int pageNo, String sortField, String sortDir, String keyword){

        int pageSize = 4;
        Pageable pageable = PageRequest.of(pageNo, pageSize,
                sortDir.equals("asc") ? Sort.by(sortField).ascending()
                        : Sort.by(sortField).descending()
        );
        if(!Objects.equals(keyword, "")){
            return productRepos.searchProduct(keyword, pageable);
        }
        return productRepos.findAll(pageable);
    }
    public List<Product> searchProductByKeyword(String keyword){
        return productRepos.searchProductByKeyword(keyword);
    }
}
