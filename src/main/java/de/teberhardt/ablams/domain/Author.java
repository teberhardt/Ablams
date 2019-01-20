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

    @Column(name = "last_name")
    private String lastName;

    @OneToMany(mappedBy = "author")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AudioBook> audioBooks = new HashSet<>();
    @OneToMany(mappedBy = "author")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BookSeries> bookSeries = new HashSet<>();
    @OneToOne(mappedBy = "author")
    @JsonIgnore
    private Image image;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
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

    public String getLastName() {
        return lastName;
    }

    public Author lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public Set<BookSeries> getBookSeries() {
        return bookSeries;
    }

    public Author bookSeries(Set<BookSeries> bookSeries) {
        this.bookSeries = bookSeries;
        return this;
    }

    public Author addBookSeries(BookSeries bookSeries) {
        this.bookSeries.add(bookSeries);
        bookSeries.setAuthor(this);
        return this;
    }

    public Author removeBookSeries(BookSeries bookSeries) {
        this.bookSeries.remove(bookSeries);
        bookSeries.setAuthor(null);
        return this;
    }

    public void setBookSeries(Set<BookSeries> bookSeries) {
        this.bookSeries = bookSeries;
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
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

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
            ", lastName='" + getLastName() + "'" +
            "}";
    }
}
