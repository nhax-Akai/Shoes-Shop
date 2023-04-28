package com.shoesshop.models;

import com.shoesshop.commons.CartConstant;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

import static com.shoesshop.utils.Time.getPresentTime;

@Entity
@Getter
@Setter
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String status;
    private Double total;
    private String createdAt;
    private String updateAt;
    @OneToMany(mappedBy = "cart")
    private Set<CartItem> cartItems;
    @OneToOne(mappedBy = "cart")
    private Receipt receipt;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;
    public Cart(){
        this.createdAt = getPresentTime();
        this.updateAt = getPresentTime();
        this.total = 0.0;
        this.status = CartConstant.ORDERING;
    }

}
