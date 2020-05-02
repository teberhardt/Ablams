package de.teberhardt.ablams.repository;

import de.teberhardt.ablams.domain.Audiobook;
import de.teberhardt.ablams.domain.Audiofile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the Audiofile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AudiofileRepository extends JpaRepository<Audiofile, Long> {

    Optional<Audiofile> findByFilePathAndAudiobook(String filePath, Audiobook relatedAudiobook);

    @Query ("Select a from Audiofile a where a.audiobook.id = :aId order by a.filePath")
    List<Audiofile> findByAudiobookId(Long aId);
}
