package de.teberhardt.ablams.web.rest.controller;


import de.teberhardt.ablams.service.ProgressableService;
import de.teberhardt.ablams.web.dto.ProgressableDTO;
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
 * REST controller for managing Progressable.
 */
@Path("/api/progressables")
@RolesAllowed("user")
public class ProgressableController {

    private final Logger log = LoggerFactory.getLogger(ProgressableController.class);

    private static final String ENTITY_NAME = "progressable";

    private final ProgressableService progressableService;

    public ProgressableController(ProgressableService progressableService) {
        this.progressableService = progressableService;
    }

    /**
     * POST  /progressables : Create a new progressable.
     *
     * @param aId the progressableDTO id to create
     * @return the ResponseEntity with status 201 (Created) and with body the new progressableDTO, or with status 400 (Bad Request) if the progressable has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @POST
    @Path("/{aid}/start")
    @Timed
    public Response createProgressable( @PathParam("aid") Long aId) throws URISyntaxException {
        ProgressableDTO result = progressableService.startOrProceedProgress(aId);
        return Response.created(new URI("/api/progressables/" + result.getId()))
            //.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .entity(result).build();
    }

    /**
     * PUT  /progressables : Updates an existing progressable.
     *
     * @param progressableDTO the progressableDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated progressableDTO,
     * or with status 400 (Bad Request) if the progressableDTO is not valid,
     * or with status 500 (Internal Server Error) if the progressableDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PUT
    @Timed
    public Response updateProgressable(ProgressableDTO progressableDTO) throws URISyntaxException {
        log.debug("REST request to update Progressable : {}", progressableDTO);
        if (progressableDTO.getId() == null) {
            throw new IllegalArgumentException("Invalid id on " + ENTITY_NAME + "due to id is null");
        }
        ProgressableDTO result = progressableService.save(progressableDTO);
        return Response.ok()
            //.headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, progressableDTO.getId().toString()))
            .entity(result).build();
    }

    /**
     * GET  /progressables : get all the progressables.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of progressables in body
     */
    @GET
    @Timed
    public List<ProgressableDTO> getAllProgressables() {
        log.debug("REST request to get all Progressables");
        return progressableService.findAll();
    }

    /**
     * GET  /progressables/:id : get the "id" progressable.
     *
     * @param id the id of the progressableDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the progressableDTO, or with status 404 (Not Found)
     */
    @GET
    @Path("/{id}")
    @Timed
    public Response getProgressable(Long id) {
        log.debug("REST request to get Progressable : {}", id);
        Optional<ProgressableDTO> progressableDTO = progressableService.findOne(id);
        return ResponseUtil.wrapOrNotFound(progressableDTO);
    }

    /**
     * DELETE  /progressables/:id : delete the "id" progressable.
     *
     * @param id the id of the progressableDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @Path("/progressables/{id}")
    @DELETE
    @Timed
    public Response deleteProgressable(Long id) {
        log.debug("REST request to delete Progressable : {}", id);
        progressableService.delete(id);
        return Response.ok()
                //.headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString()))
                .build();
    }
}
