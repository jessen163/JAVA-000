package com.jessen.demo1.entity;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private int userId;
    private String account;
    private String password;
    private String nickname;
    private String realname;
    private int status;
    private Date registerDate;
    private Date lastLoginTime;

}