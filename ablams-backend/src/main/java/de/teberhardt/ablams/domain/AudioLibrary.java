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
    private List<Audiobook> audiobooks = new ArrayList<>();
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

    public List<Audiobook> getAudiobooks() {
        return audiobooks;
    }

    public AudioLibrary audiobooks(List<Audiobook> audiobooks) {
        this.audiobooks = audiobooks;
        return this;
    }

    public AudioLibrary addAudiobook(Audiobook audiobook) {
        this.audiobooks.add(audiobook);
        audiobook.setAudioLibrary(this);
        return this;
    }

    public AudioLibrary removeAudiobook(Audiobook audiobook) {
        this.audiobooks.remove(audiobook);
        audiobook.setAudioLibrary(null);
        return this;
    }

    public void setAudiobooks(List<Audiobook> audiobooks) {
        this.audiobooks = audiobooks;
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
