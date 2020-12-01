package com.jessen.demo1.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Product {
    private int productId;
    private String productName;
    private String productCategory;
    private BigDecimal price;
    private int status;
    private Date createDate;
    private Date lastUpdateTime;

}