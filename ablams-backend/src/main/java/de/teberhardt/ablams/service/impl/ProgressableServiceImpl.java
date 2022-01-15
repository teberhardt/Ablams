package de.teberhardt.ablams.service.impl;

import de.teberhardt.ablams.domain.Progressable;
import de.teberhardt.ablams.repository.ProgressableRepository;
import de.teberhardt.ablams.service.ProgressableService;
import de.teberhardt.ablams.service.mapper.ProgressableMapper;
import de.teberhardt.ablams.web.dto.ProgressableDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Progressable.
 */
@Singleton
@Transactional
public class ProgressableServiceImpl implements ProgressableService {

    private final Logger log = LoggerFactory.getLogger(ProgressableServiceImpl.class);

    private final ProgressableRepository progressableRepository;

    private final ProgressableMapper progressableMapper;

    public ProgressableServiceImpl(ProgressableRepository progressableRepository, ProgressableMapper progressableMapper) {
        this.progressableRepository = progressableRepository;
        this.progressableMapper = progressableMapper;
    }

    /**
     * Save a progressable.
     *
     * @param progressableDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProgressableDTO save(ProgressableDTO progressableDTO) {
        log.debug("Request to save Progressable : {}", progressableDTO);

        Progressable progressable = progressableMapper.toEntity(progressableDTO);
        progressableRepository.persist(progressable);
        return progressableMapper.toDto(progressable);
    }

    /**
     * Get all the progressables.
     *
     * @return the list of entities
     */
    @Override
    @Transactional
    public List<ProgressableDTO> findAll() {
        log.debug("Request to get all Progressables");
        return progressableRepository.findAll().stream()
            .map(progressableMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one progressable by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional
    public Optional<ProgressableDTO> findOne(Long id) {
        log.debug("Request to get Progressable : {}", id);
        return Optional.of(progressableMapper.toDto(progressableRepository.findById(id)));
    }

    /**
     * Delete the progressable by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Progressable : {}", id);
        progressableRepository.deleteById(id);
    }
}
