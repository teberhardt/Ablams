package de.teberhardt.ablams.repository;

import de.teberhardt.ablams.domain.Audiobook;
import de.teberhardt.ablams.domain.AudioFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the AudioFile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AudioFileRepository extends JpaRepository<AudioFile, Long> {

    Optional<AudioFile> findByFilePathAndAudiobook(String filePath, Audiobook relatedAudiobook);

    @Query ("Select a from AudioFile a where a.audiobook.id = :aId order by a.filePath")
    List<AudioFile> findByAudiobookId(Long aId);
}
