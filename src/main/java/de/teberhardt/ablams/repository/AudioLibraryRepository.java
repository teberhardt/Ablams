package de.teberhardt.ablams.repository;

import de.teberhardt.ablams.domain.AudioLibrary;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AudioLibrary entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AudioLibraryRepository extends JpaRepository<AudioLibrary, Long> {

}
