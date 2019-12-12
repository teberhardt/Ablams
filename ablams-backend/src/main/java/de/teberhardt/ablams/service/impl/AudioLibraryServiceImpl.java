package de.teberhardt.ablams.service.impl;

import de.teberhardt.ablams.domain.AudioBook;
import de.teberhardt.ablams.domain.AudioLibrary;
import de.teberhardt.ablams.repository.AudioLibraryRepository;
import de.teberhardt.ablams.service.AudioBookService;
import de.teberhardt.ablams.service.AudioLibraryService;
import de.teberhardt.ablams.web.dto.AudioLibraryDTO;
import de.teberhardt.ablams.service.mapper.AudioLibraryMapper;
import org.apache.commons.io.FilenameUtils;
import org.jaudiotagger.audio.SupportedFileFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

/**
 * Service Implementation for managing AudioLibrary.
 */
@Service
@Transactional
public class AudioLibraryServiceImpl implements AudioLibraryService {

    private final Logger log = LoggerFactory.getLogger(AudioLibraryServiceImpl.class);

    private final AudioLibraryRepository audioLibraryRepository;
    private final AudioLibraryMapper audioLibraryMapper;
    private final AudioBookService audioBookService;

    public AudioLibraryServiceImpl(AudioLibraryRepository audioLibraryRepository, AudioLibraryMapper audioLibraryMapper, AudioBookService audioBookService) {
        this.audioLibraryRepository = audioLibraryRepository;
        this.audioLibraryMapper = audioLibraryMapper;
        this.audioBookService = audioBookService;
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

        AudioLibrary createdLibrary = audioLibraryMapper.toEntity(audioLibraryDTO);

        AudioLibrary existingLibrary = audioLibraryRepository
            .findByFilepath(createdLibrary.getFilepath())
            .orElseGet(() -> audioLibraryRepository.save(createdLibrary));

        scan(existingLibrary);
        return audioLibraryMapper.toDto(existingLibrary);
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

    @Transactional
    public void scan(AudioLibrary audioLibrary){
        Path startPath = Paths.get(audioLibrary.getFilepath()).normalize();

        if (!Files.isDirectory(startPath))
        {
            throw new IllegalArgumentException(String.format("Given Path %s represents a Directory", startPath.toString()));
        }

        Map<Path, List<Path>> collect = null;
        try {
            collect = Files.walk(startPath)
                .parallel()
                .filter(e -> !Files.isDirectory(e))
                .filter(this::isAudioFile)
                .collect(groupingBy(Path::getParent));
        } catch (IOException e) {
            log.error("Error appeared when scanning AudioLibrary won Path {}", startPath.toString(), e);
            return;
        }

        List<AudioBook> audioBooks = collect
            .entrySet()
            .stream()
            .parallel()
            .map(e -> audioBookService.scan(e.getKey(), e.getValue(), audioLibrary))
            .collect(Collectors.toList());

        audioLibrary.getAudioBooks().clear();
        audioLibrary.getAudioBooks().addAll(audioBooks);
    }

    private boolean isAudioFile(Path p)
    {
        String filename = FilenameUtils.getExtension(p.getFileName().toString());
        return isSupportedFilesuffix(filename);
    }

    private boolean isSupportedFilesuffix(String actualSuffix) {
        return Arrays
            .stream(SupportedFileFormat.values())
            .map(SupportedFileFormat::getFilesuffix)
            .anyMatch(actualSuffix::equalsIgnoreCase);
    }
}
