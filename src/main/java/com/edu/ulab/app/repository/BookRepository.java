package com.edu.ulab.app.repository;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.Book;

import java.util.List;
import java.util.Optional;


public interface BookRepository {

    Optional<Book> getById(Long id);

    void saveOrUpdate(Book book);

    void delete(Book book);

    List<Book> getBooksForUser(Long UserId);
}
