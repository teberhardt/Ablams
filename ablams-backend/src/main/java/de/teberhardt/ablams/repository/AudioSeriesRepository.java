package de.teberhardt.ablams.repository;

import de.teberhardt.ablams.domain.AudioSeries;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.inject.Singleton;


/**
 * Spring Data  repository for the AudioSeries entity.
 */
@SuppressWarnings("unused")
@Singleton
public class AudioSeriesRepository implements PanacheRepository<AudioSeries> {

}
