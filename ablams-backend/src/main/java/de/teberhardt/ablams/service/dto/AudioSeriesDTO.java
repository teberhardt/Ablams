package de.teberhardt.ablams.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the AudioSeries entity.
 */
public class AudioSeriesDTO implements Serializable {

    private Long id;

    private String seriesName;

    private Long authorId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
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

        AudioSeriesDTO audioSeriesDTO = (AudioSeriesDTO) o;
        if (audioSeriesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), audioSeriesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AudioSeriesDTO{" +
            "id=" + getId() +
            ", seriesName='" + getSeriesName() + "'" +
            ", author=" + getAuthorId() +
            "}";
    }
}
