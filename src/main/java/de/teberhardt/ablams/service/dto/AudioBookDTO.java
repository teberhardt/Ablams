package de.teberhardt.ablams.service.dto;

import java.io.Serializable;
import java.util.Objects;
import de.teberhardt.ablams.domain.enumeration.Language;

/**
 * A DTO for the AudioBook entity.
 */
public class AudioBookDTO implements Serializable {

    private Long id;

    private String name;

    private Language language;

    private Long audioLibraryId;

    private Long seriesId;

    private Long authorId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Long getAudioLibraryId() {
        return audioLibraryId;
    }

    public void setAudioLibraryId(Long audioLibraryId) {
        this.audioLibraryId = audioLibraryId;
    }

    public Long getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(Long audioSeriesId) {
        this.seriesId = audioSeriesId;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AudioBookDTO audioBookDTO = (AudioBookDTO) o;
        if (audioBookDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), audioBookDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AudioBookDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", language='" + getLanguage() + "'" +
            ", audioLibrary=" + getAudioLibraryId() +
            ", series=" + getSeriesId() +
            ", author=" + getAuthorId() +
            "}";
    }
}
