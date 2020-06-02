package de.teberhardt.ablams.web.rest.controller;


import de.teberhardt.ablams.service.AudiofileService;
import de.teberhardt.ablams.util.ResponseUtil;
import de.teberhardt.ablams.web.dto.AudiofileDTO;
import de.teberhardt.ablams.web.rest.assembler.AudiofilesRepresentationModelAssembler;
import de.teberhardt.ablams.web.rest.errors.BadRequestAlertException;
import de.teberhardt.ablams.web.rest.util.HeaderUtil;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 * REST controller for managing Audiofile.
 */
@RestController
@RequestMapping("/api/audio-files")
@ExposesResourceFor(AudiofileDTO.class)
public class AudiofileController {

    private final Logger log = LoggerFactory.getLogger(AudiofileController.class);

    private static final String ENTITY_NAME = "audiofile";

    private final AudiofileService audiofileService;

    private final AudiofilesRepresentationModelAssembler modelAssembler;

    private final PagedResourcesAssembler<AudiofileDTO> pageAssembler;

    public AudiofileController(AudiofileService audiofileService, AudiofilesRepresentationModelAssembler modelAssembler, PagedResourcesAssembler<AudiofileDTO> pageAssembler) {
        this.audiofileService = audiofileService;
        this.modelAssembler = modelAssembler;
        this.pageAssembler = pageAssembler;
    }

    /**
     * POST  /audio-files : Create a new audiofile.
     *
     * @param audiofileDTO the audiofileDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new audiofileDTO, or with status 400 (Bad Request) if the audiofile has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping
    @Timed
    public ResponseEntity<EntityModel<AudiofileDTO>> createAudiofile(@RequestBody AudiofileDTO audiofileDTO) throws URISyntaxException {
        log.debug("REST request to save Audiofile : {}", audiofileDTO);
        if (audiofileDTO.getId() != null) {
            throw new BadRequestAlertException("A new audiofile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AudiofileDTO result = audiofileService.save(audiofileDTO);
        return ResponseEntity.created(new URI("/api/audio-files/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(modelAssembler.toModel(result));
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
    @PutMapping
    @Timed
    public ResponseEntity<EntityModel<AudiofileDTO>> updateAudiofile(@RequestBody AudiofileDTO audiofileDTO) throws URISyntaxException {
        log.debug("REST request to update Audiofile : {}", audiofileDTO);
        if (audiofileDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AudiofileDTO result = audiofileService.save(audiofileDTO);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, audiofileDTO.getId().toString()))
            .body(modelAssembler.toModel(result));
    }

    /**
     * GET  /audio-files : get all the audiofiles.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of audiofiles in body
     */
    @GetMapping
    @Timed
    public PagedModel<EntityModel<AudiofileDTO>> getAllAudiofiles(Pageable pageable) {
        log.debug("REST request to get all Audiofiles");
        return pageAssembler.toModel(audiofileService.findAll(pageable), modelAssembler);
    }

    /**
     * GET  /audio-files : get all the audiofiles.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of audiofiles in body
     */
    @GetMapping("/{aId}/audio-files")
    @Timed
    public CollectionModel<EntityModel<AudiofileDTO>> getAudiofilesOfAudiobook(@PathVariable Long aId) {
        log.debug("REST request to get all Audiofiles");
        return modelAssembler.toCollectionModel(audiofileService.findbyAudiobook(aId));
    }

    /**
     * GET  /audio-files/:id : get the "id" audiofile.
     *
     * @param id the id of the audiofileDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the audiofileDTO, or with status 404 (Not Found)
     */
    @GetMapping("/{id}")
    @Timed
    public ResponseEntity<EntityModel<AudiofileDTO>> getAudiofile(@PathVariable Long id) {
        log.debug("REST request to get Audiofile : {}", id);
        Optional<AudiofileDTO> audiofileDTO = audiofileService.findOne(id);

        return ResponseUtil
            .wrapOrNotFound(
                audiofileDTO.map(modelAssembler::toModel)
            );
    }

    /**
     * DELETE  /audio-files/:id : delete the "id" audiofile.
     *
     * @param id the id of the audiofileDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/{id}")
    @Timed
    public ResponseEntity<Void> deleteAudiofile(@PathVariable Long id) {
        log.debug("REST request to delete Audiofile : {}", id);
        audiofileService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
