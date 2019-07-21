package de.teberhardt.ablams.repository;

import de.teberhardt.ablams.domain.Progressable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Progressable entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProgressableRepository extends JpaRepository<Progressable, Long> {

}
