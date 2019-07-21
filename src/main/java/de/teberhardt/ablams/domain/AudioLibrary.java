package de.teberhardt.ablams.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A AudioLibrary.
 */
@Entity
@Table(name = "audio_library")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AudioLibrary implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "filepath")
    private String filepath;

    @OneToMany(mappedBy = "audioLibrary")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AudioBook> audioBooks = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
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

    public Set<AudioBook> getAudioBooks() {
        return audioBooks;
    }

    public AudioLibrary audioBooks(Set<AudioBook> audioBooks) {
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

    public void setAudioBooks(Set<AudioBook> audioBooks) {
        this.audioBooks = audioBooks;
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
        AudioLibrary audioLibrary = (AudioLibrary) o;
        if (audioLibrary.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), audioLibrary.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AudioLibrary{" +
            "id=" + getId() +
            ", filepath='" + getFilepath() + "'" +
            "}";
    }
}
