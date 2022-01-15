package de.teberhardt.ablams.repository;

import de.teberhardt.ablams.domain.AudioLibrary;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.inject.Singleton;
import java.util.Optional;


/**
 * Spring Data  repository for the AudioLibrary entity.
 */
@SuppressWarnings("unused")
@Singleton
public class AudioLibraryRepository implements PanacheRepository<AudioLibrary> {

    public Optional<AudioLibrary> findByFilepath(String filePath){
        return Optional.empty();
    }

}
