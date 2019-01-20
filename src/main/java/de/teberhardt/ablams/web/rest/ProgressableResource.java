package de.teberhardt.ablams.web.rest;

import com.codahale.metrics.annotation.Timed;
import de.teberhardt.ablams.service.ProgressableService;
import de.teberhardt.ablams.web.rest.errors.BadRequestAlertException;
import de.teberhardt.ablams.web.rest.util.HeaderUtil;
import de.teberhardt.ablams.service.dto.ProgressableDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Progressable.
 */
@RestController
@RequestMapping("/api")
public class ProgressableResource {

    private final Logger log = LoggerFactory.getLogger(ProgressableResource.class);

    private static final String ENTITY_NAME = "progressable";

    private final ProgressableService progressableService;

    public ProgressableResource(ProgressableService progressableService) {
        this.progressableService = progressableService;
    }

    /**
     * POST  /progressables : Create a new progressable.
     *
     * @param progressableDTO the progressableDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new progressableDTO, or with status 400 (Bad Request) if the progressable has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/progressables")
    @Timed
    public ResponseEntity<ProgressableDTO> createProgressable(@RequestBody ProgressableDTO progressableDTO) throws URISyntaxException {
        log.debug("REST request to save Progressable : {}", progressableDTO);
        if (progressableDTO.getId() != null) {
            throw new BadRequestAlertException("A new progressable cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProgressableDTO result = progressableService.save(progressableDTO);
        return ResponseEntity.created(new URI("/api/progressables/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
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
    @PutMapping("/progressables")
    @Timed
    public ResponseEntity<ProgressableDTO> updateProgressable(@RequestBody ProgressableDTO progressableDTO) throws URISyntaxException {
        log.debug("REST request to update Progressable : {}", progressableDTO);
        if (progressableDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProgressableDTO result = progressableService.save(progressableDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, progressableDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /progressables : get all the progressables.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of progressables in body
     */
    @GetMapping("/progressables")
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
    @GetMapping("/progressables/{id}")
    @Timed
    public ResponseEntity<ProgressableDTO> getProgressable(@PathVariable Long id) {
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
    @DeleteMapping("/progressables/{id}")
    @Timed
    public ResponseEntity<Void> deleteProgressable(@PathVariable Long id) {
        log.debug("REST request to delete Progressable : {}", id);
        progressableService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
