package de.teberhardt.ablams.web.rest.controller;


import de.teberhardt.ablams.service.AudiofileService;
import de.teberhardt.ablams.util.ResponseUtil;
import de.teberhardt.ablams.web.dto.AudiofileDTO;
import de.teberhardt.ablams.web.rest.util.RestStream;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Audiofile.
 */
@Path("/api")
public class AudiofileController {

    private final Logger log = LoggerFactory.getLogger(AudiofileController.class);

    private static final String ENTITY_NAME = "audiofile";

    private final AudiofileService audiofileService;

    public AudiofileController(AudiofileService audiofileService) {
        this.audiofileService = audiofileService;
    }

    /**
     * POST  /audio-files : Create a new audiofile.
     *
     * @param audiofileDTO the audiofileDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new audiofileDTO, or with status 400 (Bad Request) if the audiofile has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @POST
    @Path("/audio-files")
    @Timed
    public Response createAudiofile(AudiofileDTO audiofileDTO) throws URISyntaxException {
        log.debug("REST request to save Audiofile : {}", audiofileDTO);
        if (audiofileDTO.getId() != null) {
            throw new IllegalArgumentException("A new audiofile cannot already have an ID on "+ ENTITY_NAME + ": idexists");
        }
        AudiofileDTO result = audiofileService.save(audiofileDTO);
        return Response.created(new URI("/api/audio-files/" + result.getId()))
            //.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .entity(result).build();
    }

    /**
     * PUT  /audio-files : Updates an existing audiofile.
     *
     * @param audiofileDTO the audiofileDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated audiofileDTO,
     * or with status 400 (Bad Request) if the audiofileDTO is not valid,
     * or with status 500 (Internal Server Error) if the audiofileDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PUT
    @Path("/audio-files")
    @Timed
    public Response updateAudiofile( AudiofileDTO audiofileDTO) throws URISyntaxException {
        log.debug("REST request to update Audiofile : {}", audiofileDTO);
        if (audiofileDTO.getId() == null) {
            throw new IllegalArgumentException("Invalid id on "+ ENTITY_NAME + ": idnull");
        }
        AudiofileDTO result = audiofileService.save(audiofileDTO);
        return Response.ok()
            //.headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, audiofileDTO.getId().toString()))
            .entity(result).build();
    }

    /**
     * GET  /audio-files : get all the audiofiles.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of audiofiles in body
     */
    @GET
    @Path("/audio-files")
    @Timed
    public List<AudiofileDTO> getAllAudiofiles() {
        log.debug("REST request to get all Audiofiles");
        return audiofileService.findAll();
    }

    /**
     * GET  /audio-files : get all the audiofiles.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of audiofiles in body
     */

    @GET
    @Path("/audio-books/{aId}/audio-files")
    @Timed
    public List<AudiofileDTO> getAudiofilesOfAudiobook(Long aId) {
        log.debug("REST request to get all Audiofiles");
        return audiofileService.findbyAudiobook(aId);
    }

    /**
     * GET  /audio-files/:id : get the "id" audiofile.
     *
     * @param id the id of the audiofileDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the audiofileDTO, or with status 404 (Not Found)
     */
    @GET
    @Path("/audio-files/{id}")
    @Timed
    public Response getAudiofile(@PathParam("id") Long id) {
        log.debug("REST request to get Audiofile : {}", id);
        Optional<AudiofileDTO> audiofileDTO = audiofileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(audiofileDTO);
    }

    @GET
    @Path("/audio-files/{id}/stream")
    public Response streamAudioFile(@PathParam("id") Long id) {
        RestStream restStream = audiofileService.streamFile(id);
        return Response.ok(restStream.getStreamingOutput(), restStream.getMimetype()).build();
    }


    /**
     * DELETE  /audio-files/:id : delete the "id" audiofile.
     *
     * @param id the id of the audiofileDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @Path("/audio-files/{id}")
    @DELETE
    @Timed
    public Response deleteAudiofile( Long id) {
        log.debug("REST request to delete Audiofile : {}", id);
        audiofileService.delete(id);
        return Response.ok()
                //.headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString()))
        .build();
    }
}
