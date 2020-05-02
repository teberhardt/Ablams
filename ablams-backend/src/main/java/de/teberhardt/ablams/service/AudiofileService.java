package de.teberhardt.ablams.service;

import de.teberhardt.ablams.domain.Audiobook;
import de.teberhardt.ablams.domain.Audiofile;
import de.teberhardt.ablams.web.dto.AudiofileDTO;

import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Audiofile.
 */
public interface AudiofileService {

    /**
     * Save a audiofile.
     *
     * @param audiofileDTO the entity to save
     * @return the persisted entity
     */
    AudiofileDTO save(AudiofileDTO audiofileDTO);

    /**
     * Get all the audiofiles.
     *
     * @return the list of entities
     */
    List<AudiofileDTO> findAll();


    /**
     * Get the "id" audiofile.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<AudiofileDTO> findOne(Long id);

    /**
     * Delete the "id" audiofile.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    void scan(Collection<Path> audiofilePaths, Audiobook relatedAudiobook);

    Audiofile scan(Path audiofilePath, Audiobook relatedAudiobook);

    List<AudiofileDTO> findbyAudiobook(Long aId);
}
