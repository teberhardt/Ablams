package de.teberhardt.ablams.web.rest.controller;

import de.teberhardt.ablams.service.AudiobookService;
import de.teberhardt.ablams.web.dto.AudiobookDTO;
import de.teberhardt.ablams.web.rest.assembler.AudiobookRepresentationModelAssembler;
import de.teberhardt.ablams.web.rest.errors.BadRequestAlertException;
import de.teberhardt.ablams.web.rest.util.HeaderUtil;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * REST controller for managing Audiobook.
 */
@RestController
@RequestMapping("/api/audio-books")
@ExposesResourceFor(AudiobookDTO.class)
public class AudiobookController {

    private final Logger log = LoggerFactory.getLogger(AudiobookController.class);

    private static final String ENTITY_NAME = "audiobook";

    private final AudiobookService audiobookService;

    private final AudiobookRepresentationModelAssembler assembler;

    private final PagedResourcesAssembler<AudiobookDTO> pagedAssembler;

    private final EntityLinks entityLinks;

    public AudiobookController(AudiobookService audiobookService, AudiobookRepresentationModelAssembler audiobookRepresentationModelAssembler, PagedResourcesAssembler<AudiobookDTO> pagedAssembler, EntityLinks entityLinks) {
        this.audiobookService = audiobookService;
        this.assembler = audiobookRepresentationModelAssembler;
        this.pagedAssembler = pagedAssembler;
        this.entityLinks = entityLinks;
    }

    /**
     * POST  /audio-books : Create a new audiobook.
     *
     * @param audiobookDTO the audiobookDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new audiobookDTO, or with status 400 (Bad Request) if the audiobook has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping
    @Timed
    public ResponseEntity<EntityModel<AudiobookDTO>> createAudiobook(@RequestBody AudiobookDTO audiobookDTO) throws URISyntaxException {
        log.debug("REST request to save Audiobook : {}", audiobookDTO);
        if (audiobookDTO.getId() != null) {
            throw new BadRequestAlertException("A new audiobook cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AudiobookDTO result = audiobookService.save(audiobookDTO);

       return Optional.of(result.getId()) //
            .map(id -> ResponseEntity.created( //
                linkTo(entityLinks.linkToItemResource(AudiobookDTO.class,id)).toUri()
            ).body(assembler.toModel(result)))
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * PUT  /audio-books : Updates an existing audiobook.
     *
     * @param audiobookDTO the audiobookDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated audiobookDTO,
     * or with status 400 (Bad Request) if the audiobookDTO is not valid,
     * or with status 500 (Internal Server Error) if the audiobookDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping
    @Timed
    public ResponseEntity<AudiobookDTO> updateAudiobook(@RequestBody AudiobookDTO audiobookDTO) throws URISyntaxException {
        log.debug("REST request to update Audiobook : {}", audiobookDTO);
        if (audiobookDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AudiobookDTO result = audiobookService.save(audiobookDTO);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, audiobookDTO.getId().toString()))
            .body(result);

    }

    /**
     * GET  /audio-books : get all the audiobooks.
     *
     * @param assembler assembler to create pagedmodel
     * @param pageable current page
     * @return the ResponseEntity with status 200 (OK) and the list of audiobooks in body
     */
    @GetMapping
    @Timed
    public PagedModel<EntityModel<AudiobookDTO>> getAllAudiobooks(Pageable pageable) {
        log.debug("REST request to get all Audiobooks");

        Page<AudiobookDTO> all = audiobookService.findAll(pageable);
        return pagedAssembler.toModel(all);
    }

    /**
     * GET  /audio-books/:id : get the "id" audiobook.
     *
     * @param id the id of the audiobookDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the audiobookDTO, or with status 404 (Not Found)
     */
    @GetMapping("/{id}")
    @Timed
    public ResponseEntity<EntityModel<AudiobookDTO>> getAudiobook(@PathVariable Long id) {
        log.debug("REST request to get Audiobook : {}", id);
        return audiobookService
            .findOne(id)
            .map(assembler::toModel)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity
                .notFound()
                .build()
            );
    }

    /**
     * DELETE  /audio-books/:id : delete the "id" audiobook.
     *
     * @param id the id of the audiobookDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/{id}")
    @Timed
    public ResponseEntity<Void> deleteAudiobook(@PathVariable Long id) {
        log.debug("REST request to delete Audiobook : {}", id);
        audiobookService.delete(id);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString()))
            .build();
    }
}
