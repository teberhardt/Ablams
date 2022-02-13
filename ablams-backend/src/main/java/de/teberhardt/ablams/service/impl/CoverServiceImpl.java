package de.teberhardt.ablams.service.impl;

import de.teberhardt.ablams.domain.Cover;
import de.teberhardt.ablams.repository.CoverRepository;
import de.teberhardt.ablams.service.CoverService;
import de.teberhardt.ablams.service.mapper.CoverMapper;
import de.teberhardt.ablams.web.dto.CoverDTO;
import de.teberhardt.ablams.web.rest.util.RestStream;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Image.
 */
@Singleton
@Transactional
public class CoverServiceImpl implements CoverService {

    private final Logger log = LoggerFactory.getLogger(CoverServiceImpl.class);

    private final CoverRepository coverRepository;

    private final CoverMapper coverMapper;

    public CoverServiceImpl(CoverRepository coverRepository, CoverMapper coverMapper) {
        this.coverRepository = coverRepository;
        this.coverMapper = coverMapper;
    }

    /**
     * Save a image.
     *
     * @param coverDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CoverDTO save(CoverDTO coverDTO) {
        log.debug("Request to save Cover : {}", coverDTO);

        Cover cover = coverMapper.toEntity(coverDTO);
        coverRepository.persist(cover);
        return coverMapper.toDto(cover);
    }

    /**
     * Get all the images.
     *
     * @return the list of entities
     */
    @Override
    @Transactional
    public List<CoverDTO> findAll() {
        log.debug("Request to get all Covers");
        return coverRepository.findAll().stream()
            .map(coverMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one image by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional
    public Optional<CoverDTO> findOne(Long id) {
        log.debug("Request to get Cover : {}", id);
        return Optional.of(coverMapper.toDto(coverRepository.findById(id)));
    }

    /**
     * Delete the image by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Cover : {}", id);
        coverRepository.deleteById(id);
    }

    @Override
    public Optional<CoverDTO> findCoverForAudiobookId(Long aId) {
       // return coverRepository.findCoverByAudiobookId(aId).map(coverMapper::toDto);
/*        CoverDTO cdto = new CoverDTO();
        cdto.setAudiobookId(aId);
        cdto.setAuthorId(1L);
        cdto.setFilePath("F:\\hörbucher\\Band 4 - Die Stürme des Zorns\\cover.jpg");

        return Optional.of(cdto);*/
        return coverRepository.findCoverByAudiobookId(aId).map(coverMapper::toDto);
    }

    @Override
    public RestStream streamCoverForAudiobook(Long aId){
        Optional<CoverDTO> potentialCover = findCoverForAudiobookId(aId);
        if (potentialCover.isPresent()){
            CoverDTO coverDTO = potentialCover.get();
            return RestStream.of(Paths.get(coverDTO.getFilePath()));
        } else {
            return null;
        }
    }
}
