package de.teberhardt.ablams.repository;

import de.teberhardt.ablams.domain.Audiobook;
import de.teberhardt.ablams.domain.Audiofile;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.inject.Singleton;
import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the Audiofile entity.
 */
@SuppressWarnings("unused")
@Singleton
public class AudiofileRepository implements PanacheRepository<Audiofile> {

    Optional<Audiofile> findByFilePathAndAudiobook(String filePath, Audiobook relatedAudiobook) {
        return find("file_path = :f and aid = :aid ", filePath,relatedAudiobook.getId()).firstResultOptional();
    }

    public List<Audiofile> findByAudiobookId(Long aId) {
        return find("aid", aId).list();
    }
}
