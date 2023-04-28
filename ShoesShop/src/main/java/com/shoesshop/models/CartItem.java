package com.shoesshop.models;

import lombok.Getter;
import lombok.Setter;

import javax.naming.Name;
import javax.persistence.*;

import static com.shoesshop.utils.Time.getPresentTime;

@Entity
@Getter
@Setter
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int quantity;
    private String createdAt;
    private String updateAt;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private Cart cart;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id" , referencedColumnName = "id")
    private Product product;

    public CartItem(){
        this.createdAt = getPresentTime();
        this.updateAt = getPresentTime();
    }
}
