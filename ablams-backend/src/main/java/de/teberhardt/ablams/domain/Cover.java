package de.teberhardt.ablams.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Image.
 */
@Entity
@Table(name = "cover")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Cover implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "width")
    private Integer width;

    @Column(name = "height")
    private Integer height;

    @Column(name = "bitdepth")
    private Integer bitdepth;

    @OneToOne    @JoinColumn(unique = true)
    private Audiobook audiobook;

    @OneToOne    @JoinColumn(unique = true)
    private Author author;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public Cover filePath(String filePath) {
        this.filePath = filePath;
        return this;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getWidth() {
        return width;
    }

    public Cover width(Integer width) {
        this.width = width;
        return this;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public Cover height(Integer height) {
        this.height = height;
        return this;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getBitdepth() {
        return bitdepth;
    }

    public Cover bitdepth(Integer bitdepth) {
        this.bitdepth = bitdepth;
        return this;
    }

    public void setBitdepth(Integer bitdepth) {
        this.bitdepth = bitdepth;
    }

    public Audiobook getAudiobook() {
        return audiobook;
    }

    public Cover audiobook(Audiobook audiobook) {
        this.audiobook = audiobook;
        return this;
    }

    public void setAudiobook(Audiobook audiobook) {
        this.audiobook = audiobook;
    }

    public Author getAuthor() {
        return author;
    }

    public Cover author(Author author) {
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
        Cover cover = (Cover) o;
        if (cover.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cover.getId());


    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Image{" +
            "id=" + getId() +
            ", filePath='" + getFilePath() + "'" +
            ", width=" + getWidth() +
            ", height=" + getHeight() +
            ", bitdepth=" + getBitdepth() +
            "}";
    }
}
