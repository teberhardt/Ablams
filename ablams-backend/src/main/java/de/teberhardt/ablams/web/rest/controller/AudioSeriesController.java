package de.teberhardt.ablams.web.rest.controller;

import de.teberhardt.ablams.service.AudioSeriesService;
import de.teberhardt.ablams.web.dto.AudioSeriesDTO;
import de.teberhardt.ablams.util.ResponseUtil;
import de.teberhardt.ablams.web.rest.assembler.AudioSeriesRepresentationModelAssembler;
import de.teberhardt.ablams.web.rest.errors.BadRequestAlertException;
import de.teberhardt.ablams.web.rest.util.HeaderUtil;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 * REST controller for managing AudioSeries.
 */
@RestController
@RequestMapping("/api/audio-series")
@ExposesResourceFor(AudioSeriesDTO.class)
public class AudioSeriesController {

    private final Logger log = LoggerFactory.getLogger(AudioSeriesController.class);

    private static final String ENTITY_NAME = "audioSeries";

    private final AudioSeriesService audioSeriesService;

    private final AudioSeriesRepresentationModelAssembler modelAssembler;

    private final PagedResourcesAssembler<AudioSeriesDTO> pagedAssembler;


    public AudioSeriesController(AudioSeriesService audioSeriesService, AudioSeriesRepresentationModelAssembler modelAssembler, PagedResourcesAssembler<AudioSeriesDTO> pagedResourcesAssembler) {
        this.audioSeriesService = audioSeriesService;
        this.modelAssembler = modelAssembler;
        this.pagedAssembler = pagedResourcesAssembler;
    }

    /**
     * POST  /audio-series : Create a new audioSeries.
     *
     * @param audioSeriesDTO the audioSeriesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new audioSeriesDTO, or with status 400 (Bad Request) if the audioSeries has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping
    @Timed
    public ResponseEntity<EntityModel<AudioSeriesDTO>> createAudioSeries(@RequestBody AudioSeriesDTO audioSeriesDTO) throws URISyntaxException {
        log.debug("REST request to save AudioSeries : {}", audioSeriesDTO);
        if (audioSeriesDTO.getId() != null) {
            throw new BadRequestAlertException("A new audioSeries cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AudioSeriesDTO result = audioSeriesService.save(audioSeriesDTO);

        return ResponseEntity.created(new URI("/api/audio-series/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(modelAssembler.toModel(result));
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
    @PutMapping("/audio-series")
    @Timed
    public ResponseEntity<EntityModel<AudioSeriesDTO>> updateAudioSeries(@RequestBody AudioSeriesDTO audioSeriesDTO) throws URISyntaxException {
        log.debug("REST request to update AudioSeries : {}", audioSeriesDTO);
        if (audioSeriesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        AudioSeriesDTO result = audioSeriesService.save(audioSeriesDTO);


        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, audioSeriesDTO.getId().toString()))
            .body(modelAssembler.toModel(result));
    }

    /**
     * GET  /audio-series : get all the audioSeries.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of audioSeries in body
     */
    @GetMapping
    @Timed
    public PagedModel<EntityModel<AudioSeriesDTO>> getAllAudioSeries(Pageable pageable) {
        log.debug("REST request to get all AudioSeries");
        return pagedAssembler.toModel(audioSeriesService.findAll(pageable), modelAssembler);
    }

    /**
     * GET  /audio-series/:id : get the "id" audioSeries.
     *
     * @param id the id of the audioSeriesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the audioSeriesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/{id}")
    @Timed
    public ResponseEntity<EntityModel<AudioSeriesDTO>> getAudioSeries(@PathVariable Long id) {
        log.debug("REST request to get AudioSeries : {}", id);
        Optional<AudioSeriesDTO> audioSeriesDTO = audioSeriesService.findOne(id);
        return ResponseUtil
            .wrapOrNotFound(
                audioSeriesDTO.map(modelAssembler::toModel)
            );
    }

    /**
     * DELETE  /audio-series/:id : delete the "id" audioSeries.
     *
     * @param id the id of the audioSeriesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/{id}")
    @Timed
    public ResponseEntity<Void> deleteAudioSeries(@PathVariable Long id) {
        log.debug("REST request to delete AudioSeries : {}", id);
        audioSeriesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
