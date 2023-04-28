package com.shoesshop.models;

import com.shoesshop.commons.AccountConstant;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static com.shoesshop.utils.Time.getPresentTime;

@Entity
@Getter
@Setter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String email;
    private String password;
    private String phoneNumber;
    private String role;
    private boolean isActive;
    private String createAt;
    private String updateAt;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Account(){
        this.isActive = true;
        this.role = AccountConstant.DEFAULT_ROLE;
        this.createAt = getPresentTime();
        this.updateAt = getPresentTime();
    }
}
