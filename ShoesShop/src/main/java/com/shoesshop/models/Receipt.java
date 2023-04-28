package com.shoesshop.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static com.shoesshop.utils.Time.getPresentTime;

@Entity
@Getter
@Setter
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String fullName;
    private String phoneNumber;
    private String address;
    private boolean isDelivered;
    private String createdAt;
    private String updateAt;
    @OneToOne
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private Cart cart;

    public Receipt(){
        this.createdAt = getPresentTime();
        this.updateAt = getPresentTime();
        this.isDelivered = false;
    }
}
