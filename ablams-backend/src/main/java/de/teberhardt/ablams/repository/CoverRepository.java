package de.teberhardt.ablams.repository;

import de.teberhardt.ablams.domain.Cover;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the Image entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CoverRepository extends JpaRepository<Cover, Long> {

    public Optional<Cover> findCoverByAudioBookId(Long audiobookId);

}
