package com.globant.jakarta_starter.data;

import java.util.List;
import java.util.UUID;

import com.globant.jakarta_starter.model.Book;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class BookDAO {

    @PersistenceContext(name = "BooksPU")
    private EntityManager em;

    public Book findById(UUID id) {
        return em.find(Book.class, id);
    }

    public List<Book> findAll() {
        return em.createQuery("SELECT b FROM Book b", Book.class).getResultList();
    }

    public void save(Book book) {
        em.persist(book);
    }

    public void remove(UUID id) {
        Book book = em.find(Book.class, id);
        if (book != null)
            em.remove(book);
    }

}
