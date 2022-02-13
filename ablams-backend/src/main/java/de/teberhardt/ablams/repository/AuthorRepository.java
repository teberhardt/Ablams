package de.teberhardt.ablams.repository;

import de.teberhardt.ablams.domain.Author;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.inject.Singleton;


/**
 * Spring Data  repository for the Author entity.
 */
@SuppressWarnings("unused")
@Singleton
public class AuthorRepository implements PanacheRepository<Author> {

}
