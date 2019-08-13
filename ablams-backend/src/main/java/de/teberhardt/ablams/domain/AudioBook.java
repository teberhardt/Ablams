package de.teberhardt.ablams.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.teberhardt.ablams.domain.enumeration.Language;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A AudioBook.
 */
@Entity
@Table(name = "audio_book")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AudioBook implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "language")
    private Language language;

    @Column(name = "file_path")
    private String filePath;

    @OneToMany(mappedBy = "audioBook")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AudioFile> audioFiles = new HashSet<>();
    @OneToOne(mappedBy = "audioBook")
    @JsonIgnore
    private Image image;

    @ManyToOne
    @JsonIgnoreProperties("audioBooks")
    private AudioLibrary audioLibrary;

    @ManyToOne
    @JsonIgnoreProperties("audioBooks")
    private AudioSeries series;

    @ManyToOne
    @JsonIgnoreProperties("audioBooks")
    private Author author;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public AudioBook name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Language getLanguage() {
        return language;
    }

    public AudioBook language(Language language) {
        this.language = language;
        return this;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getFilePath() {
        return filePath;
    }

    public AudioBook filePath(String filePath) {
        this.filePath = filePath;
        return this;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Set<AudioFile> getAudioFiles() {
        return audioFiles;
    }

    public AudioBook audioFiles(Set<AudioFile> audioFiles) {
        this.audioFiles = audioFiles;
        return this;
    }

    public AudioBook addAudioFile(AudioFile audioFile) {
        this.audioFiles.add(audioFile);
        audioFile.setAudioBook(this);
        return this;
    }

    public AudioBook removeAudioFile(AudioFile audioFile) {
        this.audioFiles.remove(audioFile);
        audioFile.setAudioBook(null);
        return this;
    }

    public void setAudioFiles(Set<AudioFile> audioFiles) {
        this.audioFiles = audioFiles;
    }

    public Image getImage() {
        return image;
    }

    public AudioBook image(Image image) {
        this.image = image;
        return this;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public AudioLibrary getAudioLibrary() {
        return audioLibrary;
    }

    public AudioBook audioLibrary(AudioLibrary audioLibrary) {
        this.audioLibrary = audioLibrary;
        return this;
    }

    public void setAudioLibrary(AudioLibrary audioLibrary) {
        this.audioLibrary = audioLibrary;
    }

    public AudioSeries getSeries() {
        return series;
    }

    public AudioBook series(AudioSeries audioSeries) {
        this.series = audioSeries;
        return this;
    }

    public void setSeries(AudioSeries audioSeries) {
        this.series = audioSeries;
    }

    public Author getAuthor() {
        return author;
    }

    public AudioBook author(Author author) {
        this.author = author;
        return this;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AudioBook audioBook = (AudioBook) o;
        if (audioBook.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), audioBook.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AudioBook{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", language='" + getLanguage() + "'" +
            ", filePath='" + getFilePath() + "'" +
            "}";
    }
}
