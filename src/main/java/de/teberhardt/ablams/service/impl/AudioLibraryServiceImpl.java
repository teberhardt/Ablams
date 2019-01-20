package de.teberhardt.ablams.service.impl;

import de.teberhardt.ablams.service.AudioLibraryService;
import de.teberhardt.ablams.domain.AudioLibrary;
import de.teberhardt.ablams.repository.AudioLibraryRepository;
import de.teberhardt.ablams.service.dto.AudioLibraryDTO;
import de.teberhardt.ablams.service.mapper.AudioLibraryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing AudioLibrary.
 */
@Service
@Transactional
public class AudioLibraryServiceImpl implements AudioLibraryService {

    private final Logger log = LoggerFactory.getLogger(AudioLibraryServiceImpl.class);

    private final AudioLibraryRepository audioLibraryRepository;

    private final AudioLibraryMapper audioLibraryMapper;

    private final AudioLibraryScanService audioLibraryScanService;

    public AudioLibraryServiceImpl(AudioLibraryRepository audioLibraryRepository, AudioLibraryMapper audioLibraryMapper, AudioLibraryScanService audioLibraryScanService) {
        this.audioLibraryRepository = audioLibraryRepository;
        this.audioLibraryMapper = audioLibraryMapper;
        this.audioLibraryScanService = audioLibraryScanService;
    }

    /**
     * Save a audioLibrary.
     *
     * @param audioLibraryDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AudioLibraryDTO save(AudioLibraryDTO audioLibraryDTO) {
        log.debug("Request to save AudioLibrary : {}", audioLibraryDTO);

        AudioLibrary audioLibrary = audioLibraryMapper.toEntity(audioLibraryDTO);
        audioLibrary = audioLibraryRepository.save(audioLibrary);
        try {
            audioLibraryScanService.scan(audioLibrary);
        } catch (IOException e) {
            log.error("", e);
        }
        return audioLibraryMapper.toDto(audioLibrary);
    }

    /**
     * Get all the audioLibraries.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<AudioLibraryDTO> findAll() {
        log.debug("Request to get all AudioLibraries");
        return audioLibraryRepository.findAll().stream()
            .map(audioLibraryMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one audioLibrary by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AudioLibraryDTO> findOne(Long id) {
        log.debug("Request to get AudioLibrary : {}", id);
        return audioLibraryRepository.findById(id)
            .map(audioLibraryMapper::toDto);
    }

    /**
     * Delete the audioLibrary by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AudioLibrary : {}", id);
        audioLibraryRepository.deleteById(id);
    }
}
