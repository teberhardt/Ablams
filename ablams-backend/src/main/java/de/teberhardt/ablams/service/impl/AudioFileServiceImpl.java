package de.teberhardt.ablams.service.impl;

import de.teberhardt.ablams.domain.Audiobook;
import de.teberhardt.ablams.domain.AudioFile;
import de.teberhardt.ablams.repository.AudioFileRepository;
import de.teberhardt.ablams.service.AudioFileService;
import de.teberhardt.ablams.util.PathStringUtils;
import de.teberhardt.ablams.web.dto.AudioFileDTO;
import de.teberhardt.ablams.service.mapper.AudioFileMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Path;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing AudioFile.
 */
@Service
@Transactional
public class AudioFileServiceImpl implements AudioFileService {

    private final Logger log = LoggerFactory.getLogger(AudioFileServiceImpl.class);

    private final AudioFileRepository audioFileRepository;

    private final AudioFileMapper audioFileMapper;

    public AudioFileServiceImpl(AudioFileRepository audioFileRepository, AudioFileMapper audioFileMapper) {
        this.audioFileRepository = audioFileRepository;
        this.audioFileMapper = audioFileMapper;
    }

    /**
     * Save a audioFile.
     *
     * @param audioFileDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AudioFileDTO save(AudioFileDTO audioFileDTO) {
        log.debug("Request to save AudioFile : {}", audioFileDTO);

        AudioFile audioFile = audioFileMapper.toEntity(audioFileDTO);
        audioFile = audioFileRepository.save(audioFile);
        return audioFileMapper.toDto(audioFile);
    }

    /**
     * Get all the audioFiles.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<AudioFileDTO> findAll() {
        log.debug("Request to get all AudioFiles");
        return audioFileRepository.findAll().stream()
            .map(audioFileMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one audioFile by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AudioFileDTO> findOne(Long id) {
        log.debug("Request to get AudioFile : {}", id);
        return audioFileRepository.findById(id)
            .map(audioFileMapper::toDto);
    }

    /**
     * Delete the audioFile by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AudioFile : {}", id);
        audioFileRepository.deleteById(id);
    }

    @Transactional
    public AudioFile scan(Path audioFilePath, Audiobook relatedAudiobook)
    {
        // performance issue with nio in java 8, so switch to old io
        if (!audioFilePath.toFile().exists())
        {
            throw new IllegalArgumentException(String.format("Given Path %s does not Exists", audioFilePath.toString()));
        }

        PathStringUtils pathStringUtils = new PathStringUtils(audioFilePath);
        String relPathString = pathStringUtils.getRelativeString(relatedAudiobook.getPath());

        AudioFile audioFile = relatedAudiobook.getAudioFiles()
            .stream()
                .filter(e -> e.getFilePath().equals(relPathString))
                .findAny()
            .orElseGet(() -> new AudioFile().filePath(relPathString).audiobook(relatedAudiobook));

        return audioFileRepository.save(audioFile);
    }

    @Override
    public List<AudioFileDTO> findbyAudiobook(Long aId) {
        List<AudioFile> audioFilesE = audioFileRepository.findByAudiobookId(aId);
        return audioFileMapper.toDto(audioFilesE);
    }

    @Override
    public void scan(Collection<Path> audioFilePaths, Audiobook relatedAudiobook) {

        //if there exist no audiofiles to check we can just insert
        if (relatedAudiobook.getAudioFiles().isEmpty())
        {
            for (Path filePath: audioFilePaths)
            {
                PathStringUtils pathStringUtils = new PathStringUtils(filePath);
                String relPathString = pathStringUtils.getRelativeString(relatedAudiobook.getPath());

                audioFileRepository.save(new AudioFile().filePath(relPathString).audiobook(relatedAudiobook));
            }
        }
        else
        {
            audioFilePaths.forEach( path -> scan(path, relatedAudiobook));
        }
    }
}
