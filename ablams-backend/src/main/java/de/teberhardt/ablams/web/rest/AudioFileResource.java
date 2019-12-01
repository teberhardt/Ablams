package de.teberhardt.ablams.web.rest;


import de.teberhardt.ablams.service.AudioFileService;
import de.teberhardt.ablams.web.dto.AudioFileDTO;
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
 * REST controller for managing AudioFile.
 */
@RestController
@RequestMapping("/api")
public class AudioFileResource {

    private final Logger log = LoggerFactory.getLogger(AudioFileResource.class);

    private static final String ENTITY_NAME = "audioFile";

    private final AudioFileService audioFileService;

    public AudioFileResource(AudioFileService audioFileService) {
        this.audioFileService = audioFileService;
    }

    /**
     * POST  /audio-files : Create a new audioFile.
     *
     * @param audioFileDTO the audioFileDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new audioFileDTO, or with status 400 (Bad Request) if the audioFile has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/audio-files")
    @Timed
    public ResponseEntity<AudioFileDTO> createAudioFile(@RequestBody AudioFileDTO audioFileDTO) throws URISyntaxException {
        log.debug("REST request to save AudioFile : {}", audioFileDTO);
        if (audioFileDTO.getId() != null) {
            throw new BadRequestAlertException("A new audioFile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AudioFileDTO result = audioFileService.save(audioFileDTO);
        return ResponseEntity.created(new URI("/api/audio-files/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /audio-files : Updates an existing audioFile.
     *
     * @param audioFileDTO the audioFileDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated audioFileDTO,
     * or with status 400 (Bad Request) if the audioFileDTO is not valid,
     * or with status 500 (Internal Server Error) if the audioFileDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/audio-files")
    @Timed
    public ResponseEntity<AudioFileDTO> updateAudioFile(@RequestBody AudioFileDTO audioFileDTO) throws URISyntaxException {
        log.debug("REST request to update AudioFile : {}", audioFileDTO);
        if (audioFileDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AudioFileDTO result = audioFileService.save(audioFileDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, audioFileDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /audio-files : get all the audioFiles.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of audioFiles in body
     */
    @GetMapping("/audio-files")
    @Timed
    public List<AudioFileDTO> getAllAudioFiles() {
        log.debug("REST request to get all AudioFiles");
        return audioFileService.findAll();
    }

    /**
     * GET  /audio-files/:id : get the "id" audioFile.
     *
     * @param id the id of the audioFileDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the audioFileDTO, or with status 404 (Not Found)
     */
    @GetMapping("/audio-files/{id}")
    @Timed
    public ResponseEntity<AudioFileDTO> getAudioFile(@PathVariable Long id) {
        log.debug("REST request to get AudioFile : {}", id);
        Optional<AudioFileDTO> audioFileDTO = audioFileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(audioFileDTO);
    }

    /**
     * DELETE  /audio-files/:id : delete the "id" audioFile.
     *
     * @param id the id of the audioFileDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/audio-files/{id}")
    @Timed
    public ResponseEntity<Void> deleteAudioFile(@PathVariable Long id) {
        log.debug("REST request to delete AudioFile : {}", id);
        audioFileService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
