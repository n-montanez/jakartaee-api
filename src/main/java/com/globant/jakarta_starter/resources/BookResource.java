package com.globant.jakarta_starter.resources;

import java.util.UUID;

import com.globant.jakarta_starter.model.Book;
import com.globant.jakarta_starter.model.CreateBookDTO;
import com.globant.jakarta_starter.services.BookService;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
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

    @Inject
    private BookService bookService;

    @POST
    public Response create(@Valid CreateBookDTO dto) {
        bookService.createBook(dto);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    public Response getAll() {
        return Response.ok(bookService.getAllBooks()).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") UUID id) {
        Book book = bookService.getBookById(id);

        if (book == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok(book).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") UUID id, @Valid CreateBookDTO dto) {
        bookService.updateBook(dto);
        return Response.ok(bookService.getBookById(id)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") UUID id) {
        bookService.deleteBook(id);
        return Response.noContent().build();
    }
}
