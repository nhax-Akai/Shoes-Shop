package com.shoesshop.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

import static com.shoesshop.utils.Time.getPresentTime;

@Entity
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String createdAt;
    private String updatedAt;
    @OneToMany(mappedBy = "category")
    private Set<Product> products;

    public Category(){
        this.createdAt = getPresentTime();
        this.updatedAt = getPresentTime();
    }
}
