package de.teberhardt.ablams.service.impl;

import de.teberhardt.ablams.domain.AudioBook;
import de.teberhardt.ablams.domain.AudioLibrary;
import de.teberhardt.ablams.repository.AudioBookRepository;
import de.teberhardt.ablams.service.AudioBookService;
import de.teberhardt.ablams.service.AudioFileService;
import de.teberhardt.ablams.util.PathStringUtils;
import de.teberhardt.ablams.web.dto.AudioBookDTO;
import de.teberhardt.ablams.service.mapper.AudioBookMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing AudioBook.
 */
@Service
@Transactional
public class AudioBookServiceImpl implements AudioBookService {

    private final Logger log = LoggerFactory.getLogger(AudioBookServiceImpl.class);

    private final AudioBookRepository audioBookRepository;

    private final AudioBookMapper audioBookMapper;
    private AudioFileService audioFileService;

    public AudioBookServiceImpl(AudioBookRepository audioBookRepository, AudioBookMapper audioBookMapper, AudioFileService audioFileService) {
        this.audioBookRepository = audioBookRepository;
        this.audioBookMapper = audioBookMapper;
        this.audioFileService = audioFileService;
    }

    /**
     * Save a audioBook.
     *
     * @param audioBookDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AudioBookDTO save(AudioBookDTO audioBookDTO) {
        log.debug("Request to save AudioBook : {}", audioBookDTO);

        AudioBook audioBook = audioBookMapper.toEntity(audioBookDTO);
        audioBook = audioBookRepository.save(audioBook);
        return audioBookMapper.toDto(audioBook);
    }

    /**
     * Get all the audioBooks.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<AudioBookDTO> findAll() {
        log.debug("Request to get all AudioBooks");
        return audioBookRepository.findAll().stream()
            .map(audioBookMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }



    /**
     *  get all the audioBooks where Image is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<AudioBookDTO> findAllWhereImageIsNull() {
        log.debug("Request to get all audioBooks where Image is null");
        return StreamSupport
            .stream(audioBookRepository.findAll().spliterator(), false)
            .filter(audioBook -> audioBook.getCover() == null)
            .map(audioBookMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one audioBook by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AudioBookDTO> findOne(Long id) {
        log.debug("Request to get AudioBook : {}", id);
        return audioBookRepository.findById(id)
            .map(audioBookMapper::toDto);
    }

    /**
     * Delete the audioBook by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AudioBook : {}", id);
        audioBookRepository.deleteById(id);
    }

    @Transactional
    public AudioBook scan(Path folderPath, List<Path> audioFilePaths, AudioLibrary audioLibrary) {

        PathStringUtils pathUtils = new PathStringUtils(folderPath);

        String filename = pathUtils.getFileName();
        AudioBook audioBook = audioBookRepository.findAudioBookByNameAndAudioLibraryId(filename, audioLibrary.getId())
            .orElseGet(() -> new AudioBook().name(filename));

        String relativeString = pathUtils.getRelativeString(audioLibrary.getPath());
        audioBook.setFilePath(relativeString);

        audioBook.setAudioLibrary(audioLibrary);

        audioBook = audioBookRepository.save(audioBook);

        audioFileService.scan(audioFilePaths, audioBook);

        return audioBook;
    }
}
