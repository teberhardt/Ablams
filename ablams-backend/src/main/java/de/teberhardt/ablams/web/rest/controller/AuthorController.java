package de.teberhardt.ablams.web.rest.controller;


import de.teberhardt.ablams.service.AuthorService;
import de.teberhardt.ablams.util.ResponseUtil;
import de.teberhardt.ablams.web.dto.AuthorDTO;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Author.
 */
@Path("/api")
@RolesAllowed("user")
public class AuthorController {

    private final Logger log = LoggerFactory.getLogger(AuthorController.class);

    private static final String ENTITY_NAME = "author";

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    /**
     * POST  /authors : Create a new author.
     *
     * @param authorDTO the authorDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new authorDTO, or with status 400 (Bad Request) if the author has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @POST
    @Path("/authors")
    @Timed
    public Response createAuthor( AuthorDTO authorDTO) throws URISyntaxException {
        log.debug("REST request to save Author : {}", authorDTO);
        if (authorDTO.getId() != null) {
            throw new IllegalArgumentException("A new author cannot already have an ID on" + ENTITY_NAME + ": idexists");
        }
        AuthorDTO result = authorService.save(authorDTO);
        return Response.created(new URI("/api/authors/" + result.getId()))
            //.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .entity(result).build();
    }

    /**
     * PUT  /authors : Updates an existing author.
     *
     * @param authorDTO the authorDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated authorDTO,
     * or with status 400 (Bad Request) if the authorDTO is not valid,
     * or with status 500 (Internal Server Error) if the authorDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PUT
    @Path("/authors")
    @Timed
    public Response updateAuthor(AuthorDTO authorDTO) throws URISyntaxException {
        log.debug("REST request to update Author : {}", authorDTO);
        if (authorDTO.getId() == null) {
            throw new IllegalStateException("Invalid id on " + ENTITY_NAME + ": idnull");
        }
        AuthorDTO result = authorService.save(authorDTO);
        return Response.ok()
            //.headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, authorDTO.getId().toString()))
            .entity(result).build();
    }

    /**
     * GET  /authors : get all the authors.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of authors in body
     */
    @GET
    @Path("/authors")
    @Timed
    public List<AuthorDTO> getAllAuthors(String filter) {
        if ("image-is-null".equals(filter)) {
            log.debug("REST request to get all Authors where image is null");
            return authorService.findAllWhereImageIsNull();
        }
        log.debug("REST request to get all Authors");
        return authorService.findAll();
    }

    /**
     * GET  /authors/:id : get the "id" author.
     *
     * @param id the id of the authorDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the authorDTO, or with status 404 (Not Found)
     */
    @GET
    @Path("/authors/{id}")
    @Timed
    public Response getAuthor( Long id) {
        log.debug("REST request to get Author : {}", id);
        Optional<AuthorDTO> authorDTO = authorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(authorDTO);
    }

    /**
     * DELETE  /authors/:id : delete the "id" author.
     *
     * @param id the id of the authorDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DELETE
    @Path("/authors/{id}")
    @Timed
    public Response deleteAuthor(Long id) {
        log.debug("REST request to delete Author : {}", id);
        authorService.delete(id);
        return Response.ok()
                //.headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString()))
                .build();
    }
}
