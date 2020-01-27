package de.teberhardt.ablams.web.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Cover entity.
 */
public class CoverDTO implements Serializable {

    private Long id;

    private String filePath;

    private Integer width;

    private Integer height;

    private Integer bitdepth;

    private Long audioBookId;

    private Long authorId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getBitdepth() {
        return bitdepth;
    }

    public void setBitdepth(Integer bitdepth) {
        this.bitdepth = bitdepth;
    }

    public Long getAudioBookId() {
        return audioBookId;
    }

    public void setAudioBookId(Long audioBookId) {
        this.audioBookId = audioBookId;
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

        CoverDTO coverDTO = (CoverDTO) o;
        if (coverDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), coverDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CoverDTO{" +
            "id=" + getId() +
            ", filePath='" + getFilePath() + "'" +
            ", width=" + getWidth() +
            ", height=" + getHeight() +
            ", bitdepth=" + getBitdepth() +
            ", audioBook=" + getAudioBookId() +
            ", author=" + getAuthorId() +
            "}";
    }
}
