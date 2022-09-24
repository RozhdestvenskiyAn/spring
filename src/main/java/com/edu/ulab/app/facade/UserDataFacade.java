package com.edu.ulab.app.facade;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.mapper.UserMapper;
import com.edu.ulab.app.service.BookService;
import com.edu.ulab.app.service.UserService;
import com.edu.ulab.app.web.request.UserBookRequest;
import com.edu.ulab.app.web.response.UserBookResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class UserDataFacade {
    private final UserService userService;
    private final BookService bookService;
    private final UserMapper userMapper;
    private final BookMapper bookMapper;

    public UserDataFacade(UserService userService,
                          BookService bookService,
                          UserMapper userMapper,
                          BookMapper bookMapper) {
        this.userService = userService;
        this.bookService = bookService;
        this.userMapper = userMapper;
        this.bookMapper = bookMapper;
    }

    public UserBookResponse createUserWithBooks(UserBookRequest userBookRequest) {
        log.info("Got user book create request: {}", userBookRequest);
        UserDto userDto = userMapper.userRequestToUserDto(userBookRequest.getUserRequest());
        log.info("Mapped user request: {}", userDto);

        UserDto createdUserDto = userService.createUser(userDto);
        log.info("Created user: {}", createdUserDto);

        return getUserBookResponse(userBookRequest, createdUserDto);
    }

    public UserBookResponse updateUserWithBooks(UserBookRequest userBookRequest, Long id) {
        log.info("Got user book update request: {}", userBookRequest);
        log.info("checking for a user with an id: {}", id);
        UserDto currentUserDto = userService.getUserById(id);

        UserDto updateUserDTO = userMapper.userRequestToUserDto(userBookRequest.getUserRequest());
        log.info("Mapped user request: {}", updateUserDTO);

        UserDto updatedUserDto = userService.updateUser(currentUserDto.getId(), updateUserDTO);
        log.info("Updated user: {}", updatedUserDto);

        return getUserBookResponse(userBookRequest, updatedUserDto);
    }

    private UserBookResponse getUserBookResponse(UserBookRequest userBookRequest, UserDto userDto) {
        List<Long> bookIdList = userBookRequest.getBookRequests()
                .stream()
                .filter(Objects::nonNull)
                .map(bookMapper::bookRequestToBookDto)
                .peek(bookDto -> bookDto.setUserId(userDto.getId()))
                .peek(mappedBookDto -> log.info("mapped book: {}", mappedBookDto))
                .map(bookService::createBook)
                .peek(createdBook -> log.info("Created book: {}", createdBook))
                .peek(userService::addBook)
                .peek(createdBook -> log.info("Added book : {}, for user id: {} ", createdBook, createdBook.getUserId()))
                .map(BookDto::getId)
                .toList();
        log.info("Collected book ids: {}", bookIdList);

        return UserBookResponse.builder()
                .userId(userDto.getId())
                .booksIdList(bookIdList)
                .build();
    }

    public UserBookResponse getUserWithBooks(Long userId) {
        log.info("received a request to get a user by id: {}", userId);
        UserDto userByIdDto = userService.getUserById(userId);
        log.info("Received user: {}", userByIdDto);

        List<Long> bookIdList = bookService.getBooksForUser(userByIdDto)
                .stream()
                .filter(Objects::nonNull)
                .map(BookDto::getId)
                .toList();
        log.info("Collected book ids: {}", bookIdList);

        return UserBookResponse.builder()
                .userId(userByIdDto.getId())
                .booksIdList(bookIdList)
                .build();
    }

    public void deleteUserWithBooks(Long userId) {
        log.info("received a request to delete a user by id: {}", userId);
        userService.deleteUserById(userId);
        log.info("the user has been successfully deleted");

    }
}