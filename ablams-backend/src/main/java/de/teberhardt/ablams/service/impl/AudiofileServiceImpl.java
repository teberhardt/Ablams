package de.teberhardt.ablams.service.impl;

import de.teberhardt.ablams.domain.Audiobook;
import de.teberhardt.ablams.domain.Audiofile;
import de.teberhardt.ablams.repository.AudiofileRepository;
import de.teberhardt.ablams.service.AudiofileService;
import de.teberhardt.ablams.service.mapper.AudiofileMapper;
import de.teberhardt.ablams.util.PathStringUtils;
import de.teberhardt.ablams.web.dto.AudiofileDTO;
import de.teberhardt.ablams.web.rest.util.RestStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Audiofile.
 */
@Singleton
@Transactional
public class AudiofileServiceImpl implements AudiofileService {

    private final Logger log = LoggerFactory.getLogger(AudiofileServiceImpl.class);

    private final AudiofileRepository audiofileRepository;

    private final AudiofileMapper audiofileMapper;

    public AudiofileServiceImpl(AudiofileRepository audiofileRepository, AudiofileMapper audiofileMapper) {
        this.audiofileRepository = audiofileRepository;
        this.audiofileMapper = audiofileMapper;
    }

    /**
     * Save a audiofile.
     *
     * @param audiofileDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AudiofileDTO save(AudiofileDTO audiofileDTO) {
        log.debug("Request to save Audiofile : {}", audiofileDTO);

        Audiofile audiofile = audiofileMapper.toEntity(audiofileDTO);
        audiofileRepository.persist(audiofile);
        return audiofileMapper.toDto(audiofile);
    }

    /**
     * Get all the audiofiles.
     *
     * @return the list of entities
     */
    @Override
    @Transactional
    public List<AudiofileDTO> findAll() {
        log.debug("Request to get all Audiofiles");
        return audiofileRepository.findAll().stream()
            .map(audiofileMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one audiofile by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional
    public Optional<AudiofileDTO> findOne(Long id) {
        log.debug("Request to get Audiofile : {}", id);
        return Optional.of(audiofileMapper.toDto(audiofileRepository.findById(id)));
    }

    /**
     * Delete the audiofile by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Audiofile : {}", id);
        audiofileRepository.deleteById(id);
    }

    @Transactional
    public Audiofile scan(Path audiofilePath, Audiobook relatedAudiobook) {
        if (!Files.exists(audiofilePath)) {
            throw new IllegalArgumentException(String.format("Given Path %s does not Exists", audiofilePath.toString()));
        }

        PathStringUtils pathStringUtils = new PathStringUtils(audiofilePath);
        String relPathString = pathStringUtils.getRelativeString(relatedAudiobook.getPath());

        Audiofile audiofile = relatedAudiobook.getAudiofiles()
            .stream()
                .filter(e -> e.getFilePath().equals(relPathString))
                .findAny()
            .orElseGet(() -> new Audiofile().filePath(relPathString));

        audiofileRepository.persist(audiofile);
        return audiofile;
    }

    @Override
    public List<AudiofileDTO> findbyAudiobook(Long aId) {
        List<Audiofile> audiofilesE = audiofileRepository.findByAudiobookId(aId);
        return audiofileMapper.toDto(audiofilesE);
    }

    @Override
    public RestStream streamFile(Long id) {

        String filePath = "F:\\hörbucher\\Andrzej Sapkowski - The Witcher - Band 5 - Die Dame vom See\\11 Die Dame vom See.mp3";

        return RestStream.of(Paths.get(filePath));
    }

    @Override
    public Optional<Audiofile> findFirstAudioFileOfAudioBook(Long aId, int userId) {
        return audiofileRepository.find("audiobook_id = ?1 and trackNr = ?2", aId, userId).firstResultOptional();
    }

    @Override
    public void scan(Collection<Path> audiofilePaths, Audiobook relatedAudiobook) {

        //if there exist no audiofiles to check we can just insert
        if (relatedAudiobook.getAudiofiles().isEmpty()) {
            int trackNr = 1;
            for (Path filePath: audiofilePaths) {
                PathStringUtils pathStringUtils = new PathStringUtils(filePath);
                String relPathString = pathStringUtils.getRelativeString(relatedAudiobook.getPath());

                Audiofile audiofile = new Audiofile().filePath(relPathString);
                audiofile.setTrackNr(trackNr);
                audiofile.setAudiobookId(relatedAudiobook.getId());

                audiofileRepository.persist(audiofile);

                trackNr++;
            }
        } else {
            audiofilePaths.forEach( path -> scan(path, relatedAudiobook));
        }
    }
}
