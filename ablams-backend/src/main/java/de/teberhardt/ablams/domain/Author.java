package de.teberhardt.ablams.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Author.
 */
@Entity
@Table(name = "author")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Author implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "author")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AudioBook> audioBooks = new HashSet<>();
    @OneToMany(mappedBy = "author")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AudioSeries> audioSeries = new HashSet<>();
    @OneToOne(mappedBy = "author")
    @JsonIgnore
    private Image image;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Author name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<AudioBook> getAudioBooks() {
        return audioBooks;
    }

    public Author audioBooks(Set<AudioBook> audioBooks) {
        this.audioBooks = audioBooks;
        return this;
    }

    public Author addAudioBook(AudioBook audioBook) {
        this.audioBooks.add(audioBook);
        audioBook.setAuthor(this);
        return this;
    }

    public Author removeAudioBook(AudioBook audioBook) {
        this.audioBooks.remove(audioBook);
        audioBook.setAuthor(null);
        return this;
    }

    public void setAudioBooks(Set<AudioBook> audioBooks) {
        this.audioBooks = audioBooks;
    }

    public Set<AudioSeries> getAudioSeries() {
        return audioSeries;
    }

    public Author audioSeries(Set<AudioSeries> audioSeries) {
        this.audioSeries = audioSeries;
        return this;
    }

    public Author addAudioSeries(AudioSeries audioSeries) {
        this.audioSeries.add(audioSeries);
        audioSeries.setAuthor(this);
        return this;
    }

    public Author removeAudioSeries(AudioSeries audioSeries) {
        this.audioSeries.remove(audioSeries);
        audioSeries.setAuthor(null);
        return this;
    }

    public void setAudioSeries(Set<AudioSeries> audioSeries) {
        this.audioSeries = audioSeries;
    }

    public Image getImage() {
        return image;
    }

    public Author image(Image image) {
        this.image = image;
        return this;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Author author = (Author) o;
        if (author.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), author.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Author{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
