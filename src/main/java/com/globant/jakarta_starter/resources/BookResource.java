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
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
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
    public Response getAll() {
        List<Book> books = em.createQuery("SELECT b FROM Book b", Book.class).getResultList();
        return Response.ok(books).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") UUID id) {
        Book book = em.find(Book.class, id);

        if (book == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok(book).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") UUID id, CreateBookDTO dto) {
        Book book = em.find(Book.class, id);

        if (book == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        if (!book.getTitle().equals(dto.getTitle()))
            book.setTitle(dto.getTitle());

        if (!book.getAuthor().getId().equals(dto.getAuthorId())) {
            Author newAuthor = em.find(Author.class, dto.getAuthorId());

            if (newAuthor == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Trying to update author to non existing id: " + dto.getAuthorId()).build();
            }

            book.setAuthor(newAuthor);
        }

        em.persist(book);

        return Response.ok(book).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") UUID id) {
        Book book = em.find(Book.class, id);
        if (book != null)
            em.remove(book);

        return Response.noContent().build();
    }
}
