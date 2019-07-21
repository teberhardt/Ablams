package de.teberhardt.ablams.repository;

import de.teberhardt.ablams.domain.AudioBook;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AudioBook entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AudioBookRepository extends JpaRepository<AudioBook, Long> {

}
