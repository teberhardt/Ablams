package de.teberhardt.ablams.service;

import de.teberhardt.ablams.web.dto.AudioSeriesDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing AudioSeries.
 */
public interface AudioSeriesService {

    /**
     * Save a audioSeries.
     *
     * @param audioSeriesDTO the entity to save
     * @return the persisted entity
     */
    AudioSeriesDTO save(AudioSeriesDTO audioSeriesDTO);

    /**
     * Get all the audioSeries.
     *
     * @return the list of entities
     */
    List<AudioSeriesDTO> findAll();


    /**
     * Get the "id" audioSeries.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<AudioSeriesDTO> findOne(Long id);

    /**
     * Delete the "id" audioSeries.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
