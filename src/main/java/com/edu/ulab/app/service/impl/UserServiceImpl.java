package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.entity.User;
import com.edu.ulab.app.exception.UserNotFoundException;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.mapper.UserMapper;
import com.edu.ulab.app.repository.BookRepository;
import com.edu.ulab.app.repository.UserRepository;
import com.edu.ulab.app.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;

    private final BookMapper bookMapper;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    private static long userId;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, BookMapper bookMapper, UserRepository userRepository, BookRepository bookRepository) {
        this.userMapper = userMapper;
        this.bookMapper = bookMapper;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        // сгенерировать идентификатор
        // создать пользователя
        // вернуть сохраненного пользователя со всеми необходимыми полями id
        userDto.setId(++userId);
        log.info("Set userDto id: {}", userDto);
        User user = userMapper.userDtoToUser(userDto);
        log.info("Mapped userDto to user: {}", user);
        userRepository.saveOrUpdate(user);
        log.info("Save user in storage: {}", user);
        return userDto;
    }

    @Override
    public UserDto updateUser(Long id, UserDto updateUserDTO) {
        updateUserDTO.setId(id);
        log.info("Set userDto id: {}", updateUserDTO);
        User updateUser = userMapper.userDtoToUser(updateUserDTO);
        log.info("Mapped userDto to user: {}", updateUser);
        userRepository.saveOrUpdate(updateUser);
        log.info("Update user in storage: {}", updateUser);
        return updateUserDTO;
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.getUserById(id).orElseThrow(() -> new UserNotFoundException("user with id " + id + " not found"));
        log.info("Get user: {} from storage by id: {}", user, id);
        return userMapper.userToUserDto(user);
    }

    @Override
    public void deleteUserById(Long id) {
        User user = userRepository.getUserById(id).orElseThrow(() -> new UserNotFoundException("user with id " + id + " not found"));
        log.info("Get user: {} from storage by id: {}", user, id);
        user.getBooks().stream()
                .peek(book -> log.info("Delete book from storage: {}", book))
                .forEach(bookRepository::delete);
        userRepository.delete(user);
        log.info("Delete user from storage: {}", user);
    }

    @Override
    public void addBook(BookDto bookDto) {
        Book book = bookMapper.bookDtoToBook(bookDto);
        book.setUser(userRepository.getUserById(bookDto.getUserId()).orElseThrow(() -> new UserNotFoundException("user with id " + bookDto.getUserId() + " not found")));
        log.info("Mapped bookDto to book: {}", book);
        User user = book.getUser();
        user.getBooks().add(book);
        log.info("Adding a book {} to the user's book list: {}", book, user);
        userRepository.saveOrUpdate(user);
        log.info("Update user: {} in storage", user);
    }
}
