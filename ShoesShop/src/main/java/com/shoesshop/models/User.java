package com.shoesshop.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static com.shoesshop.utils.Time.getPresentTime;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String fullName;
    private String address;
    private String phoneNumber;
    private String createdAt;
    private String updatedAt;
    @OneToOne(mappedBy = "user")
    private Account account;

    public User(){
        this.createdAt = getPresentTime();
        this.updatedAt = getPresentTime();
    }
}
