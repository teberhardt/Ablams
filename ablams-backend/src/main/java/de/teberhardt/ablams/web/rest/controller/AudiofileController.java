package de.teberhardt.ablams.web.rest.controller;


import de.teberhardt.ablams.service.AudiofileService;
import de.teberhardt.ablams.web.dto.AudiofileDTO;
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
 * REST controller for managing Audiofile.
 */
@RestController
@RequestMapping("/api")
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
    @PostMapping("/audio-files")
    @Timed
    public ResponseEntity<AudiofileDTO> createAudiofile(@RequestBody AudiofileDTO audiofileDTO) throws URISyntaxException {
        log.debug("REST request to save Audiofile : {}", audiofileDTO);
        if (audiofileDTO.getId() != null) {
            throw new BadRequestAlertException("A new audiofile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AudiofileDTO result = audiofileService.save(audiofileDTO);
        return ResponseEntity.created(new URI("/api/audio-files/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
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
    @PutMapping("/audio-files")
    @Timed
    public ResponseEntity<AudiofileDTO> updateAudiofile(@RequestBody AudiofileDTO audiofileDTO) throws URISyntaxException {
        log.debug("REST request to update Audiofile : {}", audiofileDTO);
        if (audiofileDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AudiofileDTO result = audiofileService.save(audiofileDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, audiofileDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /audio-files : get all the audiofiles.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of audiofiles in body
     */
    @GetMapping("/audio-files")
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
    @GetMapping("/audio-books/{aId}/audio-files")
    @Timed
    public List<AudiofileDTO> getAudiofilesOfAudiobook(@PathVariable Long aId) {
        log.debug("REST request to get all Audiofiles");
        return audiofileService.findbyAudiobook(aId);
    }

    /**
     * GET  /audio-files/:id : get the "id" audiofile.
     *
     * @param id the id of the audiofileDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the audiofileDTO, or with status 404 (Not Found)
     */
    @GetMapping("/audio-files/{id}")
    @Timed
    public ResponseEntity<AudiofileDTO> getAudiofile(@PathVariable Long id) {
        log.debug("REST request to get Audiofile : {}", id);
        Optional<AudiofileDTO> audiofileDTO = audiofileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(audiofileDTO);
    }

    /**
     * DELETE  /audio-files/:id : delete the "id" audiofile.
     *
     * @param id the id of the audiofileDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/audio-files/{id}")
    @Timed
    public ResponseEntity<Void> deleteAudiofile(@PathVariable Long id) {
        log.debug("REST request to delete Audiofile : {}", id);
        audiofileService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
