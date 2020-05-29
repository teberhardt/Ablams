package de.teberhardt.ablams.service.impl;

import de.teberhardt.ablams.domain.Audiobook;
import de.teberhardt.ablams.domain.AudioLibrary;
import de.teberhardt.ablams.domain.Cover;
import de.teberhardt.ablams.repository.AudiobookRepository;
import de.teberhardt.ablams.repository.CoverRepository;
import de.teberhardt.ablams.service.AudiobookService;
import de.teberhardt.ablams.service.AudiofileService;
import de.teberhardt.ablams.util.PathStringUtils;
import de.teberhardt.ablams.web.dto.AudiobookDTO;
import de.teberhardt.ablams.service.mapper.AudiobookMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing Audiobook.
 */
@Service
@Transactional
public class AudiobookServiceImpl implements AudiobookService {

    private final Logger log = LoggerFactory.getLogger(AudiobookServiceImpl.class);

    private final AudiobookRepository audiobookRepository;

    private final AudiobookMapper audiobookMapper;
    private AudiofileService audiofileService;
    private CoverPhysicalScanService coverPhysicalScanService;
    private CoverRepository coverRepository;

    public AudiobookServiceImpl(AudiobookRepository audiobookRepository, AudiobookMapper audiobookMapper, AudiofileService audiofileService, CoverPhysicalScanService coverPhysicalScanService, CoverRepository coverRepository) {
        this.audiobookRepository = audiobookRepository;
        this.audiobookMapper = audiobookMapper;
        this.audiofileService = audiofileService;
        this.coverPhysicalScanService = coverPhysicalScanService;
        this.coverRepository = coverRepository;
    }

    /**
     * Save a audiobook.
     *
     * @param audiobookDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AudiobookDTO save(AudiobookDTO audiobookDTO) {
        log.debug("Request to save Audiobook : {}", audiobookDTO);

        Audiobook audiobook = audiobookMapper.toEntity(audiobookDTO);
        audiobook = audiobookRepository.save(audiobook);
        return audiobookMapper.toDto(audiobook);
    }

    /**
     * Get all the audiobooks.
     *
     * @return the list of entities
     * @param pageable
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AudiobookDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Audiobooks");

        return audiobookRepository.findAll(pageable)
            .map(audiobookMapper::toDto);
    }

    /**
     * Get one audiobook by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AudiobookDTO> findOne(Long id) {
        log.debug("Request to get Audiobook : {}", id);
        return audiobookRepository.findById(id)
            .map(audiobookMapper::toDto);
    }

    /**
     * Delete the audiobook by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Audiobook : {}", id);
        audiobookRepository.deleteById(id);
    }

    @Transactional
    public void scan(Path folderPath, List<Path> audiofilePaths, AudioLibrary audioLibrary) {

        PathStringUtils pathUtils = new PathStringUtils(folderPath);

        String filename = pathUtils.getFileName();
        Audiobook audiobook = audiobookRepository.findAudiobookByNameAndAudioLibraryId(filename, audioLibrary.getId())
            .orElseGet(() -> new Audiobook().name(filename));

        String relativeString = pathUtils.getRelativeString(audioLibrary.getPath());
        audiobook.setFilePath(relativeString);

        audiobook.setAudioLibrary(audioLibrary);

        try {
            Cover cover = coverPhysicalScanService.scanForPhysicalCoverInPath(folderPath);
            if(cover != null)
            {
                cover.setAudiobook(audiobook);
                coverRepository.save(cover);
            }
            audiobook.setCover(cover);

        } catch (IOException e) {
            log.error("Could not Scan Cover in Audiobook {}, due to" ,audiobook.getId(), e);
        }

        audiobook = audiobookRepository.save(audiobook);

        audiofileService.scan(audiofilePaths, audiobook);
    }
}
