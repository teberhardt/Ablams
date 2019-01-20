package de.teberhardt.ablams.repository;

import de.teberhardt.ablams.domain.AudioSeries;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AudioSeries entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AudioSeriesRepository extends JpaRepository<AudioSeries, Long> {

}
