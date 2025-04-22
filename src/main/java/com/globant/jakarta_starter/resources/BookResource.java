package com.globant.jakarta_starter.resources;

import java.util.List;
import java.util.UUID;

import com.globant.jakarta_starter.model.Author;
import com.globant.jakarta_starter.model.Book;
import com.globant.jakarta_starter.model.CreateBookDTO;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
@Transactional
public class BookResource {

    @PersistenceContext(unitName = "BooksPU")
    private EntityManager em;

    @POST
    public Response create(@Valid CreateBookDTO dto) {
        Author author = em.find(Author.class, dto.getAuthorId());

        if (author == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Author not found with ID: " + dto.getAuthorId())
                    .build();
        }

        Book book = Book.builder()
                .title(dto.getTitle())
                .author(author)
                .build();
        em.persist(book);

        return Response.status(Response.Status.CREATED).entity(book).build();
    }

    @GET
    public List<Book> getAll() {
        return em.createQuery("SELECT b FROM Book b", Book.class).getResultList();
    }

    @GET
    @Path("/{id}")
    public Book getById(@PathParam("id") UUID id) {
        return em.find(Book.class, id);
    }
}
