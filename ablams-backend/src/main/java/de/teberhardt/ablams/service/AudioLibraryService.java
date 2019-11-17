package de.teberhardt.ablams.service;

import de.teberhardt.ablams.service.dto.AudioLibraryDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing AudioLibrary.
 */
public interface AudioLibraryService {

    /**
     * Save a audioLibrary.
     *
     * @param audioLibraryDTO the entity to save
     * @return the persisted entity
     */
    AudioLibraryDTO save(AudioLibraryDTO audioLibraryDTO);

    /**
     * Get all the audioLibraries.
     *
     * @return the list of entities
     */
    List<AudioLibraryDTO> findAll();


    /**
     * Get the "id" audioLibrary.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<AudioLibraryDTO> findOne(Long id);

    /**
     * Delete the "id" audioLibrary.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
