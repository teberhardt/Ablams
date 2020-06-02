package de.teberhardt.ablams.service.impl;

import de.teberhardt.ablams.domain.AudioSeries;
import de.teberhardt.ablams.repository.AudioSeriesRepository;
import de.teberhardt.ablams.service.AudioSeriesService;
import de.teberhardt.ablams.web.dto.AudioSeriesDTO;
import de.teberhardt.ablams.service.mapper.AudioSeriesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing AudioSeries.
 */
@Service
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
        audioSeries = audioSeriesRepository.save(audioSeries);
        return audioSeriesMapper.toDto(audioSeries);
    }

    /**
     * Get all the audioSeries.
     *
     * @return the list of entities
     * @param pageable
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AudioSeriesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AudioSeries");
        return audioSeriesRepository.findAll(pageable)
            .map(audioSeriesMapper::toDto);
    }


    /**
     * Get one audioSeries by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AudioSeriesDTO> findOne(Long id) {
        log.debug("Request to get AudioSeries : {}", id);
        return audioSeriesRepository.findById(id)
            .map(audioSeriesMapper::toDto);
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
