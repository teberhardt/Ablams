package de.teberhardt.ablams.repository;

import de.teberhardt.ablams.domain.AudioBook;
import de.teberhardt.ablams.domain.AudioFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the AudioFile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AudioFileRepository extends JpaRepository<AudioFile, Long> {

    Optional<AudioFile> findByFilePathAndAudioBook(String filePath, AudioBook relatedAudioBook);
}
