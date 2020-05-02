package de.teberhardt.ablams.web.rest.controller;


import de.teberhardt.ablams.service.CoverService;
import de.teberhardt.ablams.web.dto.CoverDTO;
import de.teberhardt.ablams.util.ResponseUtil;
import de.teberhardt.ablams.web.rest.errors.BadRequestAlertException;
import de.teberhardt.ablams.web.rest.util.HeaderUtil;

import io.micrometer.core.annotation.Timed;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing cover.
 */
@RestController
@RequestMapping("/api")
public class CoverController {

    private final Logger log = LoggerFactory.getLogger(CoverController.class);

    private static final String ENTITY_NAME = "cover";

    private final CoverService coverService;

    public CoverController(CoverService coverService) {
        this.coverService = coverService;
    }

    /**
     * POST  /cover : Create a new cover.
     *
     * @param coverDTO the coverDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new coverDTO, or with status 400 (Bad Request) if the cover has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cover")
    @Timed
    public ResponseEntity<CoverDTO> createCover(@RequestBody CoverDTO coverDTO) throws URISyntaxException {
        log.debug("REST request to save cover : {}", coverDTO);
        if (coverDTO.getId() != null) {
            throw new BadRequestAlertException("A new cover cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CoverDTO result = coverService.save(coverDTO);
        return ResponseEntity.created(new URI("/api/cover/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cover : Updates an existing cover.
     *
     * @param coverDTO the coverDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated coverDTO,
     * or with status 400 (Bad Request) if the coverDTO is not valid,
     * or with status 500 (Internal Server Error) if the coverDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cover")
    @Timed
    public ResponseEntity<CoverDTO> updateCover(@RequestBody CoverDTO coverDTO) throws URISyntaxException {
        log.debug("REST request to update cover : {}", coverDTO);
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
     * GET  /cover/:id : get the "id" cover.
     *
     * @param id the id of the coverDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the coverDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cover/{id}")
    @Timed
    public ResponseEntity<CoverDTO> getCover(@PathVariable Long id) {
        log.debug("REST request to get cover : {}", id);
        Optional<CoverDTO> coverDTO = coverService.findOne(id);
        return ResponseUtil.wrapOrNotFound(coverDTO);
    }

    /**
     * GET /audio-books/{aId}/cover : get bytes of the cover of "aId" audiobook.
     *
     * @param aId the id of the coverDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the coverDTO, or with status 404 (Not Found)
     */
    @GetMapping(value = "/audio-books/{aId}/cover/raw",
        produces = MediaType.IMAGE_JPEG_VALUE)
    @Timed
    public @ResponseBody
    Object getCoverAsByteByAudiobook(@PathVariable Long aId) throws IOException {
        log.debug("REST request to get cover as byte array of audiobook {}", aId);
        Optional<InputStream> coverStream = coverService.getCoverAsBytestreamForAudiobookId(aId);
        if (coverStream.isPresent()) {
            return IOUtils.toByteArray(coverStream.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    /**
     * DELETE  /cover/:id : delete the "id" cover.
     *
     * @param id the id of the coverDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cover/{id}")
    @Timed
    public ResponseEntity<Void> deleteCover(@PathVariable Long id) {
        log.debug("REST request to delete cover : {}", id);
        coverService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
