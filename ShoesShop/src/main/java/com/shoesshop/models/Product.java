package com.shoesshop.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static com.shoesshop.utils.Time.getPresentTime;

@Entity
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private Double price;
    private String description;
    private String firstImage;
    private String secondImage;
    private String thirdImage;
    private String createdAt;
    private String updatedAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private  Category category;

    public Product(){
        this.createdAt = getPresentTime();
        this.updatedAt = getPresentTime();
    }


}
