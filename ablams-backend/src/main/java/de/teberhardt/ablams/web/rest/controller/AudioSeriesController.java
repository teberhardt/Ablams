package de.teberhardt.ablams.web.rest.controller;

import de.teberhardt.ablams.service.AudioSeriesService;
import de.teberhardt.ablams.util.ResponseUtil;
import de.teberhardt.ablams.web.dto.AudioSeriesDTO;
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
 * REST controller for managing AudioSeries.
 */
@Path("/api")
@RolesAllowed("user")
public class AudioSeriesController {

    private final Logger log = LoggerFactory.getLogger(AudioSeriesController.class);

    private static final String ENTITY_NAME = "audioSeries";

    private final AudioSeriesService audioSeriesService;

    public AudioSeriesController(AudioSeriesService audioSeriesService) {
        this.audioSeriesService = audioSeriesService;
    }

    /**
     * POST  /audio-series : Create a new audioSeries.
     *
     * @param audioSeriesDTO the audioSeriesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new audioSeriesDTO, or with status 400 (Bad Request) if the audioSeries has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @Path("/audio-series")
    @POST
    @Timed
    public Response createAudioSeries(AudioSeriesDTO audioSeriesDTO) throws URISyntaxException {
        log.debug("REST request to save AudioSeries : {}", audioSeriesDTO);
        if (audioSeriesDTO.getId() != null) {
            throw new IllegalArgumentException("A new audioSeries cannot already have an ID on " + ENTITY_NAME + ": idexists");
        }
        AudioSeriesDTO result = audioSeriesService.save(audioSeriesDTO);
        return Response.created(new URI("/api/audio-series/" + result.getId()))
           // .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .entity(result).build();
    }

    /**
     * PUT  /audio-series : Updates an existing audioSeries.
     *
     * @param audioSeriesDTO the audioSeriesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated audioSeriesDTO,
     * or with status 400 (Bad Request) if the audioSeriesDTO is not valid,
     * or with status 500 (Internal Server Error) if the audioSeriesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PUT
    @Path("/audio-series")
    @Timed
    public Response updateAudioSeries( AudioSeriesDTO audioSeriesDTO) throws URISyntaxException {
        log.debug("REST request to update AudioSeries : {}", audioSeriesDTO);
        if (audioSeriesDTO.getId() == null) {
            throw new IllegalArgumentException("Invalid id on " + ENTITY_NAME + ": idnull");
        }
        AudioSeriesDTO result = audioSeriesService.save(audioSeriesDTO);
        return Response.ok()
           // .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, audioSeriesDTO.getId().toString()))
            .entity(result).build();
    }

    /**
     * GET  /audio-series : get all the audioSeries.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of audioSeries in body
     */
    @GET
    @Path("/audio-series")
    @Timed
    public List<AudioSeriesDTO> getAllAudioSeries() {
        log.debug("REST request to get all AudioSeries");
        return audioSeriesService.findAll();
    }

    /**
     * GET  /audio-series/:id : get the "id" audioSeries.
     *
     * @param id the id of the audioSeriesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the audioSeriesDTO, or with status 404 (Not Found)
     */
    @GET
    @Path("/audio-series/{id}")
    @Timed
    public Response getAudioSeries( Long id) {
        log.debug("REST request to get AudioSeries : {}", id);
        Optional<AudioSeriesDTO> audioSeriesDTO = audioSeriesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(audioSeriesDTO);
    }

    /**
     * DELETE  /audio-series/:id : delete the "id" audioSeries.
     *
     * @param id the id of the audioSeriesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DELETE
    @Path("/audio-series/{id}")
    @Timed
    public Response deleteAudioSeries( Long id) {
        log.debug("REST request to delete AudioSeries : {}", id);
        audioSeriesService.delete(id);
        return Response.ok()
                //.headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString()))
        .build();
    }
}
