package de.teberhardt.ablams.web.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Progressable entity.
 */
public class ProgressableDTO implements Serializable {

    private Long id;

    private Float progress;

    private Float duration;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getProgress() {
        return progress;
    }

    public void setProgress(Float progress) {
        this.progress = progress;
    }

    public Float getDuration() {
        return duration;
    }

    public void setDuration(Float duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProgressableDTO progressableDTO = (ProgressableDTO) o;
        if (progressableDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), progressableDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProgressableDTO{" +
            "id=" + getId() +
            ", progress=" + getProgress() +
            ", duration=" + getDuration() +
            "}";
    }
}
