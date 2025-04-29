package com.globant.jakarta_starter.data;

import java.util.List;
import java.util.UUID;

import com.globant.jakarta_starter.model.Author;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class AuthorDAO {

    @PersistenceContext(name = "BooksPU")
    private EntityManager em;

    public Author findById(UUID id) {
        return em.find(Author.class, id);
    }

    public List<Author> findAll() {
        return em.createQuery("SELECT a FROM Author a", Author.class).getResultList();
    }

    public void save(Author author) {
        em.persist(author);
    }

    public void remove(UUID id) {
        Author author = em.find(Author.class, id);
        if (author != null)
            em.remove(author);
    }

}
