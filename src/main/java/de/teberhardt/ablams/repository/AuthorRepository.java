package de.teberhardt.ablams.repository;

import de.teberhardt.ablams.domain.Author;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the Author entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    public Optional<Author> findAuthorByName(String name);
}
