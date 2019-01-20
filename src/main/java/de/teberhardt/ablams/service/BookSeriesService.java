package de.teberhardt.ablams.service;

import de.teberhardt.ablams.service.dto.BookSeriesDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing BookSeries.
 */
public interface BookSeriesService {

    /**
     * Save a bookSeries.
     *
     * @param bookSeriesDTO the entity to save
     * @return the persisted entity
     */
    BookSeriesDTO save(BookSeriesDTO bookSeriesDTO);

    /**
     * Get all the bookSeries.
     *
     * @return the list of entities
     */
    List<BookSeriesDTO> findAll();


    /**
     * Get the "id" bookSeries.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<BookSeriesDTO> findOne(Long id);

    /**
     * Delete the "id" bookSeries.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
