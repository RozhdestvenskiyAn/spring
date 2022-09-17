package com.edu.ulab.app.service;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.entity.User;

import java.util.Optional;

public interface UserService {
    User createUser(UserDto userDto);

    User updateUser(Long id,UserDto updateUserDTO);

    User getUserById(Long id);

    void deleteUserById(Long id);

    void addBook(Book book);
}
