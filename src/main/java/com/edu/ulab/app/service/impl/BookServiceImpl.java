package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.exception.BookNotFoundException;
import com.edu.ulab.app.exception.UserNotFoundException;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.repository.BookRepository;
import com.edu.ulab.app.repository.UserRepository;
import com.edu.ulab.app.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BookServiceImpl implements BookService {

    private final BookMapper bookMapper;

    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    private static long bookId;

    @Autowired
    public BookServiceImpl(BookMapper bookMapper, UserRepository userRepository, BookRepository bookRepository) {
        this.bookMapper = bookMapper;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public BookDto createBook(BookDto bookDto) {
        bookDto.setId(++bookId);
        log.info("Set bookDto id: {}", bookId);
        Long userId = bookDto.getUserId();
        Book book = bookMapper.bookDtoToBook(bookDto);
        book.setUser(userRepository.getUserById(userId).orElseThrow(() -> new UserNotFoundException("user with id " + userId + " not found")));
        log.info("Mapped bookDto to book: {}", book);
        bookRepository.saveOrUpdate(book);
        log.info("Save book in storage: {}", book);
        return bookDto;
    }

    @Override
    public BookDto updateBook(Long id, BookDto bookDto) {
        bookDto.setId(id);
        log.info("Set bookDto id: {}", bookId);
        Book book = bookMapper.bookDtoToBook(bookDto);
        log.info("Mapped bookDto to book: {}", book);
        bookRepository.saveOrUpdate(book);
        log.info("Update book in storage: {}", book);
        return bookDto;
    }

    @Override
    public BookDto getBookById(Long id) {
        log.info("Get book from storage by id: {}", id);
        return bookRepository.getById(id)
                .map(bookMapper::bookToBookDto)
                .orElseThrow(() -> new BookNotFoundException("book with id " + id + " not found"));
    }

    @Override
    public void deleteBookById(Long id) {
        log.info("Get book from storage by id: {}", id);
        Book book = bookRepository.getById(id).orElseThrow(() -> new BookNotFoundException("book with id " + id + " not found"));
        log.info("Delete book from storage by id: {}", id);
        bookRepository.delete(book);
    }

    @Override
    public List<BookDto> getBooksForUser(UserDto userDto) {
        log.info("Get book list from storage by id: {}", userDto.getId());
        List<Book> books = bookRepository.getBooksForUser(userDto.getId());
        return books.stream()
                .map(bookMapper::bookToBookDto)
                .toList();
    }
}
