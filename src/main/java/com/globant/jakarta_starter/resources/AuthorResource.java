package com.globant.jakarta_starter.resources;

import java.util.List;
import java.util.UUID;

import com.globant.jakarta_starter.model.Author;
import com.globant.jakarta_starter.model.CreateAuthorDTO;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
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

    @PersistenceContext(unitName = "BooksPU")
    private EntityManager em;

    @POST
    public Response create(CreateAuthorDTO dto) {
        Author author = Author.builder()
                .name(dto.getName())
                .build();

        em.persist(author);

        return Response.status(Response.Status.CREATED).entity(author).build();
    }

    @GET
    public Response getAll() {
        List<Author> authors = em.createQuery("SELECT a FROM Author a", Author.class).getResultList();
        return Response.status(Response.Status.OK).entity(authors).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") UUID id) {
        Author author = em.find(Author.class, id);
        if (author == null)
            return Response.status(Response.Status.NOT_FOUND).entity("Author not found with ID: " + id).build();

        return Response.status(Response.Status.OK).entity(author).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") UUID id, CreateAuthorDTO dto) {
        Author author = em.find(Author.class, id);

        if (author == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        author.setName(dto.getName());
        em.persist(author);

        return Response.ok(author).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") UUID id) {
        Author author = em.find(Author.class, id);
        if (author != null)
            em.remove(author);

        return Response.noContent().build();
    }
}
