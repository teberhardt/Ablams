package de.teberhardt.ablams.service.impl;

import de.teberhardt.ablams.domain.Author;
import de.teberhardt.ablams.repository.AuthorRepository;
import de.teberhardt.ablams.service.AuthorService;
import de.teberhardt.ablams.web.dto.AuthorDTO;
import de.teberhardt.ablams.service.mapper.AuthorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing Author.
 */
@Singleton
@Transactional
public class AuthorServiceImpl implements AuthorService {

    private final Logger log = LoggerFactory.getLogger(AuthorServiceImpl.class);

    private final AuthorRepository authorRepository;

    private final AuthorMapper authorMapper;

    public AuthorServiceImpl(AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    /**
     * Save a author.
     *
     * @param authorDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AuthorDTO save(AuthorDTO authorDTO) {
        log.debug("Request to save Author : {}", authorDTO);

        Author author = authorMapper.toEntity(authorDTO);
        authorRepository.persist(author);
        return authorMapper.toDto(author);
    }

    /**
     * Get all the authors.
     *
     * @return the list of entities
     */
    @Override
    @Transactional
    public List<AuthorDTO> findAll() {
        log.debug("Request to get all Authors");
        return authorRepository.findAll().stream()
            .map(authorMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }



    /**
     *  get all the authors where Image is null.
     *  @return the list of entities
     */
    @Transactional
    public List<AuthorDTO> findAllWhereImageIsNull() {
        log.debug("Request to get all authors where Image is null");
        return authorRepository.findAll().stream()
            .filter(author -> author.getCover() == null)
            .map(authorMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one author by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional
    public Optional<AuthorDTO> findOne(Long id) {
        log.debug("Request to get Author : {}", id);
        return Optional.of(authorMapper.toDto(authorRepository.findById(id)));

    }

    /**
     * Delete the author by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Author : {}", id);
        authorRepository.deleteById(id);
    }
}
