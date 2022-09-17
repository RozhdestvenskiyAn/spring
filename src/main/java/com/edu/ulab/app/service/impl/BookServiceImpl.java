package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.exception.UserNotFoundException;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.repository.Repository;
import com.edu.ulab.app.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BookServiceImpl implements BookService {

    private final BookMapper bookMapper;

    private final Repository repository;

    private static long id;

    @Autowired
    public BookServiceImpl(BookMapper bookMapper, Repository repository) {
        this.bookMapper = bookMapper;
        this.repository = repository;
    }

    @Override
    public Book createBook(BookDto bookDto) {
        Book book = bookMapper.bookDtoToBook(bookDto);
        book.setId(++id);
        Long userId = bookDto.getUserId();
        book.setUser(repository.getUserById(userId).orElseThrow(() -> new UserNotFoundException("user with id "+id+" not found")));
        return book;
    }

    @Override
    public BookDto updateBook(BookDto bookDto) {
        return null;
    }

    @Override
    public BookDto getBookById(Long id) {
        return null;
    }

    @Override
    public void deleteBookById(Long id) {

    }
}
