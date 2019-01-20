package de.teberhardt.ablams.repository;

import de.teberhardt.ablams.domain.BookSeries;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BookSeries entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BookSeriesRepository extends JpaRepository<BookSeries, Long> {

}
