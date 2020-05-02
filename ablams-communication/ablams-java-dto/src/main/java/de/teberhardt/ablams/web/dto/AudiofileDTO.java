package de.teberhardt.ablams.web.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Audiofile entity.
 */
public class AudiofileDTO implements Serializable {

    private Long id;

    private String fileType;

    private String filePath;

    private Long audiobookId;

    private Long progressId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Long getAudiobookId() {
        return audiobookId;
    }

    public void setAudiobookId(Long audiobookId) {
        this.audiobookId = audiobookId;
    }

    public Long getProgressId() {
        return progressId;
    }

    public void setProgressId(Long progressableId) {
        this.progressId = progressableId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AudiofileDTO audiofileDTO = (AudiofileDTO) o;
        if (audiofileDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), audiofileDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AudiofileDTO{" +
            "id=" + getId() +
            ", fileType='" + getFileType() + "'" +
            ", filePath='" + getFilePath() + "'" +
            ", audiobook=" + getAudiobookId() +
            ", progress=" + getProgressId() +
            "}";
    }
}
