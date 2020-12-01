package com.jessen.demo1.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Order {
    private int orderId;
    private int productId;
    private int userId;
    private int productNum;
    private BigDecimal totalPrice;
    private int status;
    private Date createDate;
    private Date lastUpdateTime;

}