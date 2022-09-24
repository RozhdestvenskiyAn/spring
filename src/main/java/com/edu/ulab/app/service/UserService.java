package com.edu.ulab.app.service;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.dto.UserDto;


public interface UserService {
    UserDto createUser(UserDto userDto);

    UserDto updateUser(Long id, UserDto updateUserDTO);

    UserDto getUserById(Long id);

    void deleteUserById(Long id);

    void addBook(BookDto bookDto);
}
