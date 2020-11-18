package com.jessen.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@AllArgsConstructor
@Data
public class Article {
    private int id;
    private String title;
    private String author;
    private Date creteDate;
}
