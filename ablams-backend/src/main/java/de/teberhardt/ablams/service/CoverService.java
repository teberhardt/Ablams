package de.teberhardt.ablams.service;

import de.teberhardt.ablams.web.dto.CoverDTO;
import de.teberhardt.ablams.web.rest.util.RestStream;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Image.
 */
public interface CoverService {

    /**
     * Save a image.
     *
     * @param coverDTO the entity to save
     * @return the persisted entity
     */
    CoverDTO save(CoverDTO coverDTO);

    /**
     * Get all the images.
     *
     * @return the list of entities
     */
    List<CoverDTO> findAll();


    /**
     * Get the "id" image.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CoverDTO> findOne(Long id);

    /**
     * Delete the "id" image.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    Optional<CoverDTO> findCoverForAudiobookId(Long aId);

    RestStream streamCoverForAudiobook(Long aId);
}
