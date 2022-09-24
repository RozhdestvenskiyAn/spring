package com.edu.ulab.app.service;


import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.dto.UserDto;


import java.util.List;

public interface BookService {
    BookDto createBook(BookDto bookDto);

    BookDto updateBook(Long id, BookDto bookDto);

    BookDto getBookById(Long id);

    void deleteBookById(Long id);

    List<BookDto> getBooksForUser(UserDto userDto);
}
