package de.teberhardt.ablams.repository;

import de.teberhardt.ablams.domain.AudioLibrary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the AudioLibrary entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AudioLibraryRepository extends JpaRepository<AudioLibrary, Long> {

    public Optional<AudioLibrary> findByFilepath(String filePath);

}
