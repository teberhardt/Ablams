package de.teberhardt.ablams.repository;

import de.teberhardt.ablams.domain.Progressable;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.inject.Singleton;


/**
 * Spring Data  repository for the Progressable entity.
 */
@SuppressWarnings("unused")
@Singleton
public class ProgressableRepository implements PanacheRepository<Progressable> {

}
