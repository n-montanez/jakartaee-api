package com.globant.jakarta_starter.services;

import java.util.List;
import java.util.UUID;

import com.globant.jakarta_starter.data.AuthorDAO;
import com.globant.jakarta_starter.exceptions.EntityNotFoundException;
import com.globant.jakarta_starter.model.Author;
import com.globant.jakarta_starter.model.CreateAuthorDTO;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class AuthorService {

    @Inject
    private AuthorDAO authorDAO;

    public Author getAuthorById(UUID id) {
        Author author = authorDAO.findById(id);
        if (author == null)
            throw new EntityNotFoundException("Author", id);
        return author;
    }

    public List<Author> getAllAuthors() {
        return authorDAO.findAll();
    }

    public void createAuthor(CreateAuthorDTO authorDTO) {
        Author author = Author.builder()
                .name(authorDTO.getName())
                .build();

        authorDAO.save(author);
    }

    public void updateAuthor(CreateAuthorDTO authorDTO) {
        Author author = Author.builder()
                .name(authorDTO.getName())
                .build();

        authorDAO.save(author);
    }

    public void deleteAuthor(UUID id) {
        authorDAO.remove(id);
    }
}
