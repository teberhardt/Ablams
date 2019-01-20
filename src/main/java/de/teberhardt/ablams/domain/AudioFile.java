package de.teberhardt.ablams.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import de.teberhardt.ablams.domain.enumeration.FileType;

/**
 * A AudioFile.
 */
@Entity
@Table(name = "audio_file")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AudioFile implements Serializable {

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
    @JsonIgnoreProperties("audioFiles")
    private AudioLibrary audioLibrary;

    @ManyToOne
    @JsonIgnoreProperties("audioFiles")
    private AudioBook audioBook;

    @ManyToOne
    @JsonIgnoreProperties("audioFiles")
    private Progressable progress;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FileType getFileType() {
        return fileType;
    }

    public AudioFile fileType(FileType fileType) {
        this.fileType = fileType;
        return this;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public String getFilePath() {
        return filePath;
    }

    public AudioFile filePath(String filePath) {
        this.filePath = filePath;
        return this;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public AudioLibrary getAudioLibrary() {
        return audioLibrary;
    }

    public AudioFile audioLibrary(AudioLibrary audioLibrary) {
        this.audioLibrary = audioLibrary;
        return this;
    }

    public void setAudioLibrary(AudioLibrary audioLibrary) {
        this.audioLibrary = audioLibrary;
    }

    public AudioBook getAudioBook() {
        return audioBook;
    }

    public AudioFile audioBook(AudioBook audioBook) {
        this.audioBook = audioBook;
        return this;
    }

    public void setAudioBook(AudioBook audioBook) {
        this.audioBook = audioBook;
    }

    public Progressable getProgress() {
        return progress;
    }

    public AudioFile progress(Progressable progressable) {
        this.progress = progressable;
        return this;
    }

    public void setProgress(Progressable progressable) {
        this.progress = progressable;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AudioFile audioFile = (AudioFile) o;
        if (audioFile.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), audioFile.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AudioFile{" +
            "id=" + getId() +
            ", fileType='" + getFileType() + "'" +
            ", filePath='" + getFilePath() + "'" +
            "}";
    }
}
