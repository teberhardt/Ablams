package de.teberhardt.ablams.web.rest;


import de.teberhardt.ablams.service.CoverService;
import de.teberhardt.ablams.web.dto.CoverDTO;
import de.teberhardt.ablams.util.ResponseUtil;
import de.teberhardt.ablams.web.rest.errors.BadRequestAlertException;
import de.teberhardt.ablams.web.rest.util.HeaderUtil;

import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Image.
 */
@RestController
@RequestMapping("/api")
public class CoverResource {

    private final Logger log = LoggerFactory.getLogger(CoverResource.class);

    private static final String ENTITY_NAME = "cover";

    private final CoverService coverService;

    public CoverResource(CoverService coverService) {
        this.coverService = coverService;
    }

    /**
     * POST  /cover : Create a new image.
     *
     * @param coverDTO the imageDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new imageDTO, or with status 400 (Bad Request) if the image has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cover")
    @Timed
    public ResponseEntity<CoverDTO> createCover(@RequestBody CoverDTO coverDTO) throws URISyntaxException {
        log.debug("REST request to save Image : {}", coverDTO);
        if (coverDTO.getId() != null) {
            throw new BadRequestAlertException("A new image cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CoverDTO result = coverService.save(coverDTO);
        return ResponseEntity.created(new URI("/api/cover/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cover : Updates an existing image.
     *
     * @param coverDTO the imageDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated imageDTO,
     * or with status 400 (Bad Request) if the imageDTO is not valid,
     * or with status 500 (Internal Server Error) if the imageDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cover")
    @Timed
    public ResponseEntity<CoverDTO> updateCover(@RequestBody CoverDTO coverDTO) throws URISyntaxException {
        log.debug("REST request to update Image : {}", coverDTO);
        if (coverDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CoverDTO result = coverService.save(coverDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, coverDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cover : get all the cover.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cover in body
     */
    @GetMapping("/cover")
    @Timed
    public List<CoverDTO> getAllCovers() {
        log.debug("REST request to get all cover");
        return coverService.findAll();
    }

    /**
     * GET  /cover/:id : get the "id" image.
     *
     * @param id the id of the imageDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the imageDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cover/{id}")
    @Timed
    public ResponseEntity<CoverDTO> getCover(@PathVariable Long id) {
        log.debug("REST request to get Image : {}", id);
        Optional<CoverDTO> imageDTO = coverService.findOne(id);
        return ResponseUtil.wrapOrNotFound(imageDTO);
    }

    /**
     * DELETE  /cover/:id : delete the "id" image.
     *
     * @param id the id of the imageDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cover/{id}")
    @Timed
    public ResponseEntity<Void> deleteCover(@PathVariable Long id) {
        log.debug("REST request to delete Image : {}", id);
        coverService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
