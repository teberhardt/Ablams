package de.teberhardt.ablams.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * A AudioLibrary.
 */
@Entity
@Table(name = "audio_library")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AudioLibrary implements LocalPersisted,Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "filepath")
    private String filepath;

    @OneToMany(mappedBy = "audioLibrary", cascade = CascadeType.ALL, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private List<AudioBook> audioBooks = new ArrayList<>();
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilepath() {
        return filepath;
    }

    public AudioLibrary filepath(String filepath) {
        this.filepath = filepath;
        return this;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public List<AudioBook> getAudioBooks() {
        return audioBooks;
    }

    public AudioLibrary audioBooks(List<AudioBook> audioBooks) {
        this.audioBooks = audioBooks;
        return this;
    }

    public AudioLibrary addAudioBook(AudioBook audioBook) {
        this.audioBooks.add(audioBook);
        audioBook.setAudioLibrary(this);
        return this;
    }

    public AudioLibrary removeAudioBook(AudioBook audioBook) {
        this.audioBooks.remove(audioBook);
        audioBook.setAudioLibrary(null);
        return this;
    }

    public void setAudioBooks(List<AudioBook> audioBooks) {
        this.audioBooks = audioBooks;
    }

    @Override
    public String toString() {
        return "AudioLibrary{" +
            "id=" + getId() +
            ", filepath='" + getFilepath() + "'" +
            "}";
    }

    @Transient
    @Override
    public Path getPath() {
        return Paths.get(getFilepath());
    }
}
