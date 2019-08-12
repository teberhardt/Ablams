package de.teberhardt.ablams.service;

import de.teberhardt.ablams.domain.AudioBook;
import de.teberhardt.ablams.domain.AudioLibrary;
import de.teberhardt.ablams.service.dto.AudioBookDTO;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing AudioBook.
 */
public interface AudioBookService {

    /**
     * Save a audioBook.
     *
     * @param audioBookDTO the entity to save
     * @return the persisted entity
     */
    AudioBookDTO save(AudioBookDTO audioBookDTO);

    /**
     * Get all the audioBooks.
     *
     * @return the list of entities
     */
    List<AudioBookDTO> findAll();
    /**
     * Get all the AudioBookDTO where Image is null.
     *
     * @return the list of entities
     */
    List<AudioBookDTO> findAllWhereImageIsNull();


    /**
     * Get the "id" audioBook.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<AudioBookDTO> findOne(Long id);

    /**
     * Delete the "id" audioBook.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    AudioBook scan(Path folderPath, List<Path> audioFilePaths, AudioLibrary audioLibrary);
}
