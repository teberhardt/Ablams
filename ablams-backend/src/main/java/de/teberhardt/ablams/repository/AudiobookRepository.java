package de.teberhardt.ablams.repository;

import de.teberhardt.ablams.domain.Audiobook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the Audiobook entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AudiobookRepository extends JpaRepository<Audiobook, Long> {

    public Optional<Audiobook> findAudiobookByNameAndAudioLibraryId(String name, Long audioLibraryId);
}
