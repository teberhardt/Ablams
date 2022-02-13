package de.teberhardt.ablams.service.impl;

import de.teberhardt.ablams.domain.AudioSeries;
import de.teberhardt.ablams.repository.AudioSeriesRepository;
import de.teberhardt.ablams.service.AudioSeriesService;
import de.teberhardt.ablams.service.mapper.AudioSeriesMapper;
import de.teberhardt.ablams.web.dto.AudioSeriesDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing AudioSeries.
 */
@Singleton
@Transactional
public class AudioSeriesServiceImpl implements AudioSeriesService {

    private final Logger log = LoggerFactory.getLogger(AudioSeriesServiceImpl.class);

    private final AudioSeriesRepository audioSeriesRepository;

    private final AudioSeriesMapper audioSeriesMapper;

    public AudioSeriesServiceImpl(AudioSeriesRepository audioSeriesRepository, AudioSeriesMapper audioSeriesMapper) {
        this.audioSeriesRepository = audioSeriesRepository;
        this.audioSeriesMapper = audioSeriesMapper;
    }

    /**
     * Save a audioSeries.
     *
     * @param audioSeriesDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AudioSeriesDTO save(AudioSeriesDTO audioSeriesDTO) {
        log.debug("Request to save AudioSeries : {}", audioSeriesDTO);

        AudioSeries audioSeries = audioSeriesMapper.toEntity(audioSeriesDTO);
        audioSeriesRepository.persist(audioSeries);
        return audioSeriesMapper.toDto(audioSeries);
    }

    /**
     * Get all the audioSeries.
     *
     * @return the list of entities
     */
    @Override
    @Transactional
    public List<AudioSeriesDTO> findAll() {
        log.debug("Request to get all AudioSeries");
        return audioSeriesRepository.findAll().stream()
            .map(audioSeriesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one audioSeries by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional
    public Optional<AudioSeriesDTO> findOne(Long id) {
        log.debug("Request to get AudioSeries : {}", id);
        return Optional.of(audioSeriesMapper.toDto(audioSeriesRepository.findById(id)));
    }

    /**
     * Delete the audioSeries by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AudioSeries : {}", id);
        audioSeriesRepository.deleteById(id);
    }
}
