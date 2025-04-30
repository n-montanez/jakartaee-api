package com.globant.jakarta_starter.resources;

import java.util.UUID;

import com.globant.jakarta_starter.model.Author;
import com.globant.jakarta_starter.model.CreateAuthorDTO;
import com.globant.jakarta_starter.services.AuthorService;

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

@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
@Transactional
public class AuthorResource {

    @Inject
    private AuthorService authorService;

    @POST
    public Response create(@Valid CreateAuthorDTO dto) {
        Author author = authorService.createAuthor(dto);
        return Response.status(Response.Status.CREATED).entity(author).build();
    }

    @GET
    public Response getAll() {
        return Response.ok(authorService.getAllAuthors()).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") UUID id) {
        return Response.status(Response.Status.OK).entity(authorService.getAuthorById(id)).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") UUID id, @Valid CreateAuthorDTO dto) {
        Author author = authorService.updateAuthor(dto);
        return Response.ok(authorService.getAuthorById(id)).entity(author).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") UUID id) {
        authorService.deleteAuthor(id);
        return Response.noContent().build();
    }
}
