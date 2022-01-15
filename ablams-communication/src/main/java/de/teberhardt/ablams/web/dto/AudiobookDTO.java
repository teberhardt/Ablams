package de.teberhardt.ablams.web.dto;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Audiobook entity.
 */
public class AudiobookDTO implements Serializable {

    @Nullable
    private Long id;

    private String name;

    private String language;

    private String filePath;

    private Long audioLibraryId;

    private Long seriesId;

    private Long authorId;

    @Nullable
    public Long getId() {
        return id;
    }

    public void setId(@Nullable Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
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

        AudiobookDTO audiobookDTO = (AudiobookDTO) o;
        if (audiobookDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), audiobookDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AudiobookDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", language='" + getLanguage() + "'" +
            ", filePath='" + getFilePath() + "'" +
            ", audioLibrary=" + getAudioLibraryId() +
            ", series=" + getSeriesId() +
            ", author=" + getAuthorId() +
            "}";
    }
}
