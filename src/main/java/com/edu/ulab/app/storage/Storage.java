package com.edu.ulab.app.storage;


import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.entity.User;
import com.edu.ulab.app.repository.BookRepository;
import com.edu.ulab.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.HashMap;
import java.util.List;
import java.util.Optional;


@Component
public class Storage implements UserRepository, BookRepository {
    //todo создать хранилище в котором будут содержаться данные
    // сделать абстракции через которые можно будет производить операции с хранилищем
    // продумать логику поиска и сохранения
    // продумать возможные ошибки
    // учесть, что при сохранеии юзера или книги, должен генерироваться идентификатор
    // продумать что у узера может быть много книг и нужно создать эту связь
    // так же учесть, что методы хранилища принимают друго тип данных - учесть это в абстракции
    private final HashMap<Long, User> users;
    private final HashMap<Long, Book> books;


    @Autowired
    public Storage() {
        this.books = new HashMap<>();
        this.users = new HashMap<>();
    }

    @Override
    public void saveOrUpdate(User user) {
        users.put(user.getId(), user);
    }

    @Override
    public Optional<User> getUserById(Long userId) {
        return Optional.ofNullable(users.get(userId));
    }

    @Override
    public void delete(User user) {
        users.remove(user.getId());
    }

    @Override
    public Optional<Book> getById(Long id) {
        return Optional.ofNullable(books.get(id));
    }

    @Override
    public void saveOrUpdate(Book book) {
        books.put(book.getId(), book);
    }

    @Override
    public void delete(Book book) {
        books.remove(book.getId());
    }

    @Override
    public List<Book> getBooksForUser(Long userId) {
        return books.values().stream()
                .filter(book -> book.getUser().getId() == userId)
                .toList();
    }
}
