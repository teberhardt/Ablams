package de.teberhardt.ablams.repository;

import de.teberhardt.ablams.domain.Progressable;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.inject.Singleton;
import java.util.Optional;


/**
 * Spring Data  repository for the Progressable entity.
 */
@SuppressWarnings("unused")
@Singleton
public class ProgressableRepository implements PanacheRepository<Progressable> {

    public Optional<Progressable> findbyAudiobookIdAndUserid(long audiobookId, long userId){
        return find("audiobook_id = ?1 and userid = ?2", audiobookId, userId).stream().findAny();
    }
}
