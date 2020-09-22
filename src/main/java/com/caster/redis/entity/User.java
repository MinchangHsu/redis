package com.caster.redis.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class User implements Serializable {

    private int userId;

    private String userName;

    private String userPassword;

    private Date createDate;

    public User() {
        this.createDate = new Date();
    }

    public User(int userId, String userName, String userPassword) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.createDate = new Date();
    }
}
