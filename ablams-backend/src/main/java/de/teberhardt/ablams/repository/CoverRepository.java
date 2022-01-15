package de.teberhardt.ablams.repository;

import de.teberhardt.ablams.domain.Cover;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.inject.Singleton;
import java.util.Optional;


/**
 * Spring Data  repository for the Image entity.
 */
@SuppressWarnings("unused")
@Singleton
public class CoverRepository implements PanacheRepository<Cover> {

    public Optional<Cover> findCoverByAudiobookId(Long audiobookId){
        return Optional.empty();
    }

}
