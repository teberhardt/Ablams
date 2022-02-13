package de.teberhardt.ablams.repository;

import de.teberhardt.ablams.domain.Audiobook;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.inject.Singleton;
import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the Audiobook entity.
 */
@SuppressWarnings("unused")
@Singleton
public class AudiobookRepository implements PanacheRepository<Audiobook> {

    public Optional<Audiobook> findAudiobookByNameAndAudioLibraryId(String name, Long audioLibraryId){
        return Optional.empty();
    }
}
