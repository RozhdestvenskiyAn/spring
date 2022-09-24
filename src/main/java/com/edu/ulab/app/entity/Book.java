package com.edu.ulab.app.entity;

import lombok.Data;
import lombok.ToString;

@Data
public class Book {
    private Long id;
    @ToString.Exclude
    private User user;
    private String title;
    private String author;
    private long pageCount;
}
