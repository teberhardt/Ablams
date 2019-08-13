package de.teberhardt.ablams.service;

import de.teberhardt.ablams.service.dto.ProgressableDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Progressable.
 */
public interface ProgressableService {

    /**
     * Save a progressable.
     *
     * @param progressableDTO the entity to save
     * @return the persisted entity
     */
    ProgressableDTO save(ProgressableDTO progressableDTO);

    /**
     * Get all the progressables.
     *
     * @return the list of entities
     */
    List<ProgressableDTO> findAll();


    /**
     * Get the "id" progressable.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ProgressableDTO> findOne(Long id);

    /**
     * Delete the "id" progressable.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
