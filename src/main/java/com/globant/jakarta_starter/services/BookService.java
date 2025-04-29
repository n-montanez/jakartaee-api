package com.globant.jakarta_starter.services;

import java.util.List;
import java.util.UUID;

import com.globant.jakarta_starter.data.AuthorDAO;
import com.globant.jakarta_starter.data.BookDAO;
import com.globant.jakarta_starter.exceptions.EntityNotFoundException;
import com.globant.jakarta_starter.model.Author;
import com.globant.jakarta_starter.model.Book;
import com.globant.jakarta_starter.model.CreateBookDTO;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class BookService {

    @Inject
    private BookDAO bookDAO;

    @Inject
    private AuthorDAO authorDAO;

    public Book getBookById(UUID id) {
        Book book = bookDAO.findById(id);
        if (book == null)
            throw new EntityNotFoundException("Book", id);
        return book;
    }

    public List<Book> getAllBooks() {
        return bookDAO.findAll();
    }

    public void createBook(CreateBookDTO bookDTO) {
        Author author = authorDAO.findById(bookDTO.getAuthorId());

        if (author == null)
            throw new EntityNotFoundException("Author", bookDTO.getAuthorId());

        Book book = Book.builder()
                .title(bookDTO.getTitle())
                .author(author)
                .build();

        bookDAO.save(book);
    }

    public void updateBook(CreateBookDTO bookDTO) {
        Book book = Book.builder()
                .title(bookDTO.getTitle())
                .build();

        bookDAO.save(book);
    }

    public void deleteBook(UUID id) {
        bookDAO.remove(id);
    }
}
