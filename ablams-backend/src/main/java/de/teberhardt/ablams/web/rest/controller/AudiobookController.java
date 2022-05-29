package de.teberhardt.ablams.web.rest.controller;

import de.teberhardt.ablams.service.AudiobookService;
import de.teberhardt.ablams.util.ResponseUtil;
import de.teberhardt.ablams.web.dto.AudiobookDTO;
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
 * REST controller for managing Audiobook.
 */
@Path("/api")
@RolesAllowed("user")
public class AudiobookController {

    private final Logger log = LoggerFactory.getLogger(AudiobookController.class);

    private static final String ENTITY_NAME = "audiobook";

    private final AudiobookService audiobookService;

    public AudiobookController(AudiobookService audiobookService) {
        this.audiobookService = audiobookService;
    }

    /**
     * POST  /audio-books : Create a new audiobook.
     *
     * @param audiobookDTO the audiobookDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new audiobookDTO, or with status 400 (Bad Request) if the audiobook has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @POST
    @Path("/audio-books")
    @Timed
    public Response createAudiobook(AudiobookDTO audiobookDTO) throws URISyntaxException {
        log.debug("REST request to save Audiobook : {}", audiobookDTO);
        if (audiobookDTO.getId() != null) {
            throw new IllegalArgumentException("A new audiobook cannot already have an ID on" + ENTITY_NAME +": idexists");
        }
        AudiobookDTO result = audiobookService.save(audiobookDTO);
        return Response.created(new URI("/api/audio-books/" + result.getId()))
            //.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .entity(result).build();
    }

    /**
     * PUT  /audio-books : Updates an existing audiobook.
     *
     * @param audiobookDTO the audiobookDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated audiobookDTO,
     * or with status 400 (Bad Request) if the audiobookDTO is not valid,
     * or with status 500 (Internal Server Error) if the audiobookDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PUT
    @Path("/audio-books")
    @Timed
    public Response updateAudiobook( AudiobookDTO audiobookDTO) throws URISyntaxException {
        log.debug("REST request to update Audiobook : {}", audiobookDTO);
        if (audiobookDTO.getId() == null) {
            throw new IllegalArgumentException("Invalid id on "+ENTITY_NAME+ ":idnull");
        }
        AudiobookDTO result = audiobookService.save(audiobookDTO);
        return Response.ok()
            //.headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, audiobookDTO.getId().toString()))
            .entity(result).build();
    }

    /**
     * GET  /audio-books : get all the audiobooks.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of audiobooks in body
     */
    @GET
    @Path("/audio-books")
    @Timed
    public List<AudiobookDTO> getAllAudiobooks(String filter) {
        if ("image-is-null".equals(filter)) {
            log.debug("REST request to get all Audiobooks where image is null");
            return audiobookService.findAllWhereImageIsNull();
        }
        log.debug("REST request to get all Audiobooks");
        return audiobookService.findAll();
    }

    /**
     * GET  /audio-books/:id : get the "id" audiobook.
     *
     * @param id the id of the audiobookDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the audiobookDTO, or with status 404 (Not Found)
     */
    @Path("/audio-books/{id}")
    @GET
    @Timed
    public Response getAudiobook( Long id) {
        log.debug("REST request to get Audiobook : {}", id);
        Optional<AudiobookDTO> audiobookDTO = audiobookService.findOne(id);
        return ResponseUtil.wrapOrNotFound(audiobookDTO);
    }

    /**
     * DELETE  /audio-books/:id : delete the "id" audiobook.
     *
     * @param id the id of the audiobookDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @Path("/audio-books/{id}")
    @DELETE
    @Timed
    public Response deleteAudiobook( Long id) {
        log.debug("REST request to delete Audiobook : {}", id);
        audiobookService.delete(id);
        return Response.ok()
                //.headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString()))
                .build();
    }
}
