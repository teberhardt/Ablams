package de.teberhardt.ablams.web.rest;

import de.teberhardt.ablams.service.AudioBookService;
import de.teberhardt.ablams.service.dto.AudioBookDTO;
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
 * REST controller for managing AudioBook.
 */
@RestController
@RequestMapping("/api")
public class AudioBookResource {

    private final Logger log = LoggerFactory.getLogger(AudioBookResource.class);

    private static final String ENTITY_NAME = "audioBook";

    private final AudioBookService audioBookService;

    public AudioBookResource(AudioBookService audioBookService) {
        this.audioBookService = audioBookService;
    }

    /**
     * POST  /audio-books : Create a new audioBook.
     *
     * @param audioBookDTO the audioBookDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new audioBookDTO, or with status 400 (Bad Request) if the audioBook has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/audio-books")
    @Timed
    public ResponseEntity<AudioBookDTO> createAudioBook(@RequestBody AudioBookDTO audioBookDTO) throws URISyntaxException {
        log.debug("REST request to save AudioBook : {}", audioBookDTO);
        if (audioBookDTO.getId() != null) {
            throw new BadRequestAlertException("A new audioBook cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AudioBookDTO result = audioBookService.save(audioBookDTO);
        return ResponseEntity.created(new URI("/api/audio-books/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /audio-books : Updates an existing audioBook.
     *
     * @param audioBookDTO the audioBookDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated audioBookDTO,
     * or with status 400 (Bad Request) if the audioBookDTO is not valid,
     * or with status 500 (Internal Server Error) if the audioBookDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/audio-books")
    @Timed
    public ResponseEntity<AudioBookDTO> updateAudioBook(@RequestBody AudioBookDTO audioBookDTO) throws URISyntaxException {
        log.debug("REST request to update AudioBook : {}", audioBookDTO);
        if (audioBookDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AudioBookDTO result = audioBookService.save(audioBookDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, audioBookDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /audio-books : get all the audioBooks.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of audioBooks in body
     */
    @GetMapping("/audio-books")
    @Timed
    public List<AudioBookDTO> getAllAudioBooks(@RequestParam(required = false) String filter) {
        if ("image-is-null".equals(filter)) {
            log.debug("REST request to get all AudioBooks where image is null");
            return audioBookService.findAllWhereImageIsNull();
        }
        log.debug("REST request to get all AudioBooks");
        return audioBookService.findAll();
    }

    /**
     * GET  /audio-books/:id : get the "id" audioBook.
     *
     * @param id the id of the audioBookDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the audioBookDTO, or with status 404 (Not Found)
     */
    @GetMapping("/audio-books/{id}")
    @Timed
    public ResponseEntity<AudioBookDTO> getAudioBook(@PathVariable Long id) {
        log.debug("REST request to get AudioBook : {}", id);
        Optional<AudioBookDTO> audioBookDTO = audioBookService.findOne(id);
        return ResponseUtil.wrapOrNotFound(audioBookDTO);
    }

    /**
     * DELETE  /audio-books/:id : delete the "id" audioBook.
     *
     * @param id the id of the audioBookDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/audio-books/{id}")
    @Timed
    public ResponseEntity<Void> deleteAudioBook(@PathVariable Long id) {
        log.debug("REST request to delete AudioBook : {}", id);
        audioBookService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
