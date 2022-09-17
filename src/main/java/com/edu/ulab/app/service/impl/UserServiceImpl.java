package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.entity.User;
import com.edu.ulab.app.exception.UserNotFoundException;
import com.edu.ulab.app.mapper.UserMapper;
import com.edu.ulab.app.repository.Repository;
import com.edu.ulab.app.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private Repository repository;

    private static long id;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, Repository repository) {
        this.userMapper = userMapper;
        this.repository = repository;
    }

    @Override
    public User createUser(UserDto userDto) {
                // сгенерировать идентификатор
                // создать пользователя
                // вернуть сохраненного пользователя со всеми необходимыми полями id
        User user = userMapper.userDtoToUser(userDto);
        user.setId(++id);
        repository.saveOrUpdate(user);
        return user;
    }

    @Override
    public User updateUser(Long id, UserDto updateUserDTO) {
        User updateUser = userMapper.userDtoToUser(updateUserDTO);
        updateUser.setId(id);
        repository.saveOrUpdate(updateUser);
        return updateUser;
    }

    @Override
    public User getUserById(Long id) {
        return repository.getUserById(id).orElseThrow(() -> new UserNotFoundException("user with id "+id+" not found"));
    }

    @Override
    public void deleteUserById(Long id) {
        User user = repository.getUserById(id).orElseThrow(() -> new UserNotFoundException("user with id " + id + " not found"));
        repository.delete(user);
    }

    @Override
    public void addBook(Book book) {
        User user = book.getUser();
        user.getBooks().add(book);
        repository.saveOrUpdate(user);
    }
}
