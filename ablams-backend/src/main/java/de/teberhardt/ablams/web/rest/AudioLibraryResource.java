package de.teberhardt.ablams.web.rest;


import de.teberhardt.ablams.service.AudioLibraryService;
import de.teberhardt.ablams.web.dto.AudioLibraryDTO;
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
 * REST controller for managing AudioLibrary.
 */
@RestController
@RequestMapping("/api")
public class AudioLibraryResource {

    private final Logger log = LoggerFactory.getLogger(AudioLibraryResource.class);

    private static final String ENTITY_NAME = "audioLibrary";

    private final AudioLibraryService audioLibraryService;

    public AudioLibraryResource(AudioLibraryService audioLibraryService) {
        this.audioLibraryService = audioLibraryService;
    }

    /**
     * POST  /audio-libraries : Create a new audioLibrary.
     *
     * @param audioLibraryDTO the audioLibraryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new audioLibraryDTO, or with status 400 (Bad Request) if the audioLibrary has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/audio-libraries")
    @Timed
    public ResponseEntity<AudioLibraryDTO> createAudioLibrary(@RequestBody AudioLibraryDTO audioLibraryDTO) throws URISyntaxException {
        log.debug("REST request to save AudioLibrary : {}", audioLibraryDTO);
        if (audioLibraryDTO.getId() != null) {
            throw new BadRequestAlertException("A new audioLibrary cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AudioLibraryDTO result = audioLibraryService.save(audioLibraryDTO);
        return ResponseEntity.created(new URI("/api/audio-libraries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
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
    @PutMapping("/audio-libraries")
    @Timed
    public ResponseEntity<AudioLibraryDTO> updateAudioLibrary(@RequestBody AudioLibraryDTO audioLibraryDTO) throws URISyntaxException {
        log.debug("REST request to update AudioLibrary : {}", audioLibraryDTO);
        if (audioLibraryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AudioLibraryDTO result = audioLibraryService.save(audioLibraryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, audioLibraryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /audio-libraries : get all the audioLibraries.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of audioLibraries in body
     */
    @GetMapping("/audio-libraries")
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
    @GetMapping("/audio-libraries/{id}")
    @Timed
    public ResponseEntity<AudioLibraryDTO> getAudioLibrary(@PathVariable Long id) {
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
    @DeleteMapping("/audio-libraries/{id}")
    @Timed
    public ResponseEntity<Void> deleteAudioLibrary(@PathVariable Long id) {
        log.debug("REST request to delete AudioLibrary : {}", id);
        audioLibraryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
