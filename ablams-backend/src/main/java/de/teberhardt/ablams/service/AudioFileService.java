package de.teberhardt.ablams.service;

import de.teberhardt.ablams.domain.AudioBook;
import de.teberhardt.ablams.domain.AudioFile;
import de.teberhardt.ablams.web.dto.AudioFileDTO;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing AudioFile.
 */
public interface AudioFileService {

    /**
     * Save a audioFile.
     *
     * @param audioFileDTO the entity to save
     * @return the persisted entity
     */
    AudioFileDTO save(AudioFileDTO audioFileDTO);

    /**
     * Get all the audioFiles.
     *
     * @return the list of entities
     */
    List<AudioFileDTO> findAll();


    /**
     * Get the "id" audioFile.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<AudioFileDTO> findOne(Long id);

    /**
     * Delete the "id" audioFile.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    AudioFile scan(AudioBook audioBook, Path e);
}
