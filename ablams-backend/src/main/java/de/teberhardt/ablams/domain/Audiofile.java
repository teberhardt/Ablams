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
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Audiofile implements LocalPersisted, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "file_type")
    private FileType fileType;

    @Column(name = "file_path")
    private String filePath;

    @ManyToOne
    @JsonIgnoreProperties("audiofiles")
    private Audiobook audiobook;

    @ManyToOne
    @JsonIgnoreProperties("audiofiles")
    private Progressable progress;

    @Transient
    private AudioCharacteristics audioCharacteristics = new AudioCharacteristics();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Audiobook getAudiobook() {
        return audiobook;
    }

    public Audiofile audiobook(Audiobook audiobook) {
        this.audiobook = audiobook;
        return this;
    }

    public void setAudiobook(Audiobook audiobook) {
        this.audiobook = audiobook;
    }

    public Progressable getProgress() {
        return progress;
    }

    public Audiofile progress(Progressable progressable) {
        this.progress = progressable;
        return this;
    }

    public void setProgress(Progressable progressable) {
        this.progress = progressable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Audiofile audiofile = (Audiofile) o;
        if (audiofile.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), audiofile.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Audiofile{" +
            "id=" + getId() +
            ", fileType='" + getFileType() + "'" +
            ", filePath='" + getFilePath() + "'" +
            "}";
    }

    @Transient
    @Override
    public Path getPath() {
        return Paths.get(getAudiobook().getAudioLibrary().getFilepath(), getAudiobook().getFilePath(), getFilePath());
    }

    public AudioCharacteristics getAudioCharacteristics() {
        return audioCharacteristics;
    }

    public void setAudioCharacteristics(AudioCharacteristics audioCharacteristics) {
        this.audioCharacteristics = audioCharacteristics;
    }
}
