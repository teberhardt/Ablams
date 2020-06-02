package de.teberhardt.ablams.web.rest.controller;


import de.teberhardt.ablams.service.AuthorService;
import de.teberhardt.ablams.web.dto.AuthorDTO;
import de.teberhardt.ablams.util.ResponseUtil;
import de.teberhardt.ablams.web.rest.assembler.AuthorRepresentationModelAssembler;
import de.teberhardt.ablams.web.rest.errors.BadRequestAlertException;
import de.teberhardt.ablams.web.rest.util.HeaderUtil;

import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 * REST controller for managing Author.
 */
@RestController
@RequestMapping("/api/authors")
@ExposesResourceFor(AuthorDTO.class)
public class AuthorController {

    private final Logger log = LoggerFactory.getLogger(AuthorController.class);

    private static final String ENTITY_NAME = "author";

    private final AuthorService authorService;

    private final AuthorRepresentationModelAssembler modelAssembler;

    private final PagedResourcesAssembler<AuthorDTO> pagedAssembler;

    public AuthorController(AuthorService authorService, AuthorRepresentationModelAssembler modelAssembler, PagedResourcesAssembler<AuthorDTO> pagedAssembler) {
        this.authorService = authorService;
        this.modelAssembler = modelAssembler;
        this.pagedAssembler = pagedAssembler;
    }

    /**
     * POST  /authors : Create a new author.
     *
     * @param authorDTO the authorDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new authorDTO, or with status 400 (Bad Request) if the author has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping
    @Timed
    public ResponseEntity<EntityModel<AuthorDTO>> createAuthor(@RequestBody AuthorDTO authorDTO) throws URISyntaxException {
        log.debug("REST request to save Author : {}", authorDTO);
        if (authorDTO.getId() != null) {
            throw new BadRequestAlertException("A new author cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AuthorDTO result = authorService.save(authorDTO);

        return ResponseEntity
            .created(
                new URI("/api/authors/" + result.getId())
            )
            .headers(
                HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())
            )
            .body(
                modelAssembler.toModel(result)
            );
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
    @PutMapping
    @Timed
    public ResponseEntity<EntityModel<AuthorDTO>> updateAuthor(@RequestBody AuthorDTO authorDTO) throws URISyntaxException {
        log.debug("REST request to update Author : {}", authorDTO);
        if (authorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AuthorDTO result = authorService.save(authorDTO);

        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, authorDTO.getId().toString())
            )
            .body(
                modelAssembler.toModel(result)
            );
    }

    /**
     * GET  /authors : get all the authors.
     *
     * @param pageable the page of the request
     * @return the ResponseEntity with status 200 (OK) and the list of authors in body
     */
    @GetMapping
    @Timed
    public PagedModel<EntityModel<AuthorDTO>> getAllAuthors(Pageable pageable) {

        log.debug("REST request to get all Authors");
        return pagedAssembler.toModel(authorService.findAll(pageable), modelAssembler);
    }

    /**
     * GET  /authors/:id : get the "id" author.
     *
     * @param id the id of the authorDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the authorDTO, or with status 404 (Not Found)
     */
    @GetMapping("/{id}")
    @Timed
    public ResponseEntity<EntityModel<AuthorDTO>> getAuthor(@PathVariable Long id) {
        log.debug("REST request to get Author : {}", id);
        Optional<AuthorDTO> authorDTO = authorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(authorDTO.map(modelAssembler::toModel));
    }

    /**
     * DELETE  /authors/:id : delete the "id" author.
     *
     * @param id the id of the authorDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/{id}")
    @Timed
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        log.debug("REST request to delete Author : {}", id);
        authorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
