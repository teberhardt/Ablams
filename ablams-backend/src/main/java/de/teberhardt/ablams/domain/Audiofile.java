package de.teberhardt.ablams.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.teberhardt.ablams.domain.enumeration.FileType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * A Audiofile.
 */
@Entity
@Table(name = "audio_file")
@IdClass(BookTracknrCK.class)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Audiofile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private int trackNr;

    @Id
    @Column(name = "audiobook_id")
    private long audiobookId;

    @Enumerated(EnumType.STRING)
    @Column(name = "file_type")
    private FileType fileType;

    @Column(name = "file_path")
    private String filePath;

    @Transient
    private AudioCharacteristics audioCharacteristics = new AudioCharacteristics();

    public FileType getFileType() {
        return fileType;
    }

    public Audiofile fileType(FileType fileType) {
        this.fileType = fileType;
        return this;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public String getFilePath() {
        return filePath;
    }

    public Audiofile filePath(String filePath) {
        this.filePath = filePath;
        return this;
    }

    public Audiofile trackNr(int trackNr) {
        this.trackNr = trackNr;
        return this;
    }


    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public long getAudiobookId() {
        return audiobookId;
    }

    public void setAudiobookId(long audiobookId) {
        this.audiobookId = audiobookId;
    }

    public void setTrackNr(int trackNr) {
        this.trackNr = trackNr;
    }

    public int getTrackNr() {
        return trackNr;
    }

    public AudioCharacteristics getAudioCharacteristics() {
        return audioCharacteristics;
    }

    public void setAudioCharacteristics(AudioCharacteristics audioCharacteristics) {
        this.audioCharacteristics = audioCharacteristics;
    }
}
