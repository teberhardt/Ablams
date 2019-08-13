package de.teberhardt.ablams.repository;

import de.teberhardt.ablams.domain.AudioFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AudioFile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AudioFileRepository extends JpaRepository<AudioFile, Long> {

}
