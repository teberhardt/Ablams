package de.teberhardt.ablams.service.impl;

import de.teberhardt.ablams.service.BookSeriesService;
import de.teberhardt.ablams.domain.BookSeries;
import de.teberhardt.ablams.repository.BookSeriesRepository;
import de.teberhardt.ablams.service.dto.BookSeriesDTO;
import de.teberhardt.ablams.service.mapper.BookSeriesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing BookSeries.
 */
@Service
@Transactional
public class BookSeriesServiceImpl implements BookSeriesService {

    private final Logger log = LoggerFactory.getLogger(BookSeriesServiceImpl.class);

    private final BookSeriesRepository bookSeriesRepository;

    private final BookSeriesMapper bookSeriesMapper;

    public BookSeriesServiceImpl(BookSeriesRepository bookSeriesRepository, BookSeriesMapper bookSeriesMapper) {
        this.bookSeriesRepository = bookSeriesRepository;
        this.bookSeriesMapper = bookSeriesMapper;
    }

    /**
     * Save a bookSeries.
     *
     * @param bookSeriesDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BookSeriesDTO save(BookSeriesDTO bookSeriesDTO) {
        log.debug("Request to save BookSeries : {}", bookSeriesDTO);

        BookSeries bookSeries = bookSeriesMapper.toEntity(bookSeriesDTO);
        bookSeries = bookSeriesRepository.save(bookSeries);
        return bookSeriesMapper.toDto(bookSeries);
    }

    /**
     * Get all the bookSeries.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<BookSeriesDTO> findAll() {
        log.debug("Request to get all BookSeries");
        return bookSeriesRepository.findAll().stream()
            .map(bookSeriesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one bookSeries by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BookSeriesDTO> findOne(Long id) {
        log.debug("Request to get BookSeries : {}", id);
        return bookSeriesRepository.findById(id)
            .map(bookSeriesMapper::toDto);
    }

    /**
     * Delete the bookSeries by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BookSeries : {}", id);
        bookSeriesRepository.deleteById(id);
    }
}
