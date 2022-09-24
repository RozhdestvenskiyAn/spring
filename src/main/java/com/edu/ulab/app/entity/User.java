package com.edu.ulab.app.entity;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
public class User {
    private long id;
    private String fullName;
    private String title;
    private int age;
    @ToString.Exclude()
    private List<Book> books = new ArrayList<>();
}
