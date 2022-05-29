package de.teberhardt.ablams.web.rest.controller;


import de.teberhardt.ablams.service.AudioLibraryService;
import de.teberhardt.ablams.web.dto.AudioLibraryDTO;
import de.teberhardt.ablams.util.ResponseUtil;

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
 * REST controller for managing AudioLibrary.
 */
@Path("/api/audio-libraries")
@RolesAllowed("user")

public class AudioLibraryController {

    private final Logger log = LoggerFactory.getLogger(AudioLibraryController.class);

    private static final String ENTITY_NAME = "audioLibrary";

    private final AudioLibraryService audioLibraryService;

    public AudioLibraryController(AudioLibraryService audioLibraryService) {
        this.audioLibraryService = audioLibraryService;
    }

    /**
     * POST  /audio-libraries : Create a new audioLibrary.
     *
     * @param audioLibraryDTO the audioLibraryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new audioLibraryDTO, or with status 400 (Bad Request) if the audioLibrary has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @POST
    @Timed
    public Response createAudioLibrary(AudioLibraryDTO audioLibraryDTO) throws URISyntaxException {
        log.debug("REST request to save AudioLibrary : {}", audioLibraryDTO);
        if (audioLibraryDTO.getId() != null) {
            throw new IllegalArgumentException("A new audioLibrary cannot already have an ID on" + ENTITY_NAME + ": idexists");
        }
        AudioLibraryDTO result = audioLibraryService.save(audioLibraryDTO);
        return Response.created(new URI("/api/audio-libraries/" + result.getId()))
           // .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .entity(result).build();
    }

    /**
     * PUT  /audio-libraries : Updates an existing audioLibrary.
     *
     * @param audioLibraryDTO the audioLibraryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated audioLibraryDTO,
     * or with status 400 (Bad Request) if the audioLibraryDTO is not valid,
     * or with status 500 (Internal Server Error) if the audioLibraryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PUT
    @Timed
    public Response updateAudioLibrary(AudioLibraryDTO audioLibraryDTO) throws URISyntaxException {
        log.debug("REST request to update AudioLibrary : {}", audioLibraryDTO);
        if (audioLibraryDTO.getId() == null) {
            throw new IllegalArgumentException("Invalid id on " + ENTITY_NAME + ": idnull");
        }
        AudioLibraryDTO result = audioLibraryService.save(audioLibraryDTO);
        return Response.ok(result)
            //.headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, audioLibraryDTO.getId().toString()))
            .build();
    }

    /**
     * GET  /audio-libraries : get all the audioLibraries.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of audioLibraries in body
     */
    @GET
    @Timed
    public List<AudioLibraryDTO> getAllAudioLibraries() {
        log.debug("REST request to get all AudioLibraries");
        return audioLibraryService.findAll();
    }

    /**
     * GET  /audio-libraries/:id : get the "id" audioLibrary.
     *
     * @param id the id of the audioLibraryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the audioLibraryDTO, or with status 404 (Not Found)
     */
    @GET
    @Path("/{id}")
    @Timed
    public Response getAudioLibrary(Long id) {
        log.debug("REST request to get AudioLibrary : {}", id);
        Optional<AudioLibraryDTO> audioLibraryDTO = audioLibraryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(audioLibraryDTO);
    }

    /**
     * DELETE  /audio-libraries/:id : delete the "id" audioLibrary.
     *
     * @param id the id of the audioLibraryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @Path("/{id}")
    @DELETE
    @Timed
    public Response deleteAudioLibrary( Long id) {
        log.debug("REST request to delete AudioLibrary : {}", id);
        audioLibraryService.delete(id);
        return Response.ok()
                //.headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString()))
                .build();
    }
}
