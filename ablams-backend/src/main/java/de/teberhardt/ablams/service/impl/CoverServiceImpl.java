package de.teberhardt.ablams.service.impl;

import de.teberhardt.ablams.domain.Cover;
import de.teberhardt.ablams.repository.CoverRepository;
import de.teberhardt.ablams.service.CoverService;
import de.teberhardt.ablams.service.mapper.CoverMapper;
import de.teberhardt.ablams.web.dto.CoverDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Image.
 */
@Service
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
        cover = coverRepository.save(cover);
        return coverMapper.toDto(cover);
    }

    /**
     * Get all the images.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
    public Optional<CoverDTO> findOne(Long id) {
        log.debug("Request to get Cover : {}", id);
        return coverRepository.findById(id)
            .map(coverMapper::toDto);
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
}
