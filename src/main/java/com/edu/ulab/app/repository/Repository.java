package com.edu.ulab.app.repository;

import com.edu.ulab.app.entity.User;

import java.util.Optional;


public interface Repository {
    void saveOrUpdate(User user);
    Optional<User> getUserById(Long userId);

    void delete(User user);
}
