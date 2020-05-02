package de.teberhardt.ablams.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.teberhardt.ablams.domain.enumeration.Language;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * A Audiobook.
 */
@Entity
@Table(name = "audio_book")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Audiobook implements LocalPersisted,Serializable  {

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

    @OneToMany(mappedBy = "audiobook", cascade = CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private List<AudioFile> audioFiles = new ArrayList<>();
    @OneToOne(mappedBy = "audiobook")
    @JsonIgnore
    private Cover cover;

    @ManyToOne
    @JsonIgnoreProperties("audiobooks")
    private AudioLibrary audioLibrary;

    @ManyToOne
    @JsonIgnoreProperties("audiobooks")
    private AudioSeries series;

    @ManyToOne
    @JsonIgnoreProperties("audiobooks")
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

    public Audiobook name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Language getLanguage() {
        return language;
    }

    public Audiobook language(Language language) {
        this.language = language;
        return this;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getFilePath() {
        return filePath;
    }

    public Audiobook filePath(String filePath) {
        this.filePath = filePath;
        return this;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public List<AudioFile> getAudioFiles() {
        return audioFiles;
    }

    public Audiobook audioFiles(List<AudioFile> audioFiles) {
        this.audioFiles = audioFiles;
        return this;
    }

    public Audiobook addAudioFile(AudioFile audioFile) {
        this.audioFiles.add(audioFile);
        audioFile.setAudiobook(this);
        return this;
    }

    public Audiobook removeAudioFile(AudioFile audioFile) {
        this.audioFiles.remove(audioFile);
        audioFile.setAudiobook(null);
        return this;
    }

    public void setAudioFiles(List<AudioFile> audioFiles) {
        this.audioFiles = audioFiles;
    }

    public Cover getCover() {
        return cover;
    }

    public Audiobook image(Cover cover) {
        this.cover = cover;
        return this;
    }

    public void setCover(Cover cover) {
        this.cover = cover;
    }

    public AudioLibrary getAudioLibrary() {
        return audioLibrary;
    }

    public Audiobook audioLibrary(AudioLibrary audioLibrary) {
        this.audioLibrary = audioLibrary;
        return this;
    }

    public void setAudioLibrary(AudioLibrary audioLibrary) {
        this.audioLibrary = audioLibrary;
    }

    public AudioSeries getSeries() {
        return series;
    }

    public Audiobook series(AudioSeries audioSeries) {
        this.series = audioSeries;
        return this;
    }

    public void setSeries(AudioSeries audioSeries) {
        this.series = audioSeries;
    }

    public Author getAuthor() {
        return author;
    }

    public Audiobook author(Author author) {
        this.author = author;
        return this;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Transient
    @Override
    public Path getPath()
    {
        return Paths.get(getAudioLibrary().getFilepath(), getFilePath());
    }

    @Override
    public String toString() {
        return "Audiobook{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", language='" + getLanguage() + "'" +
            ", filePath='" + getFilePath() + "'" +
            "}";
    }
}
