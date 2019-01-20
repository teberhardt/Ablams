package de.teberhardt.ablams.service.dto;

import java.io.Serializable;
import java.util.Objects;
import de.teberhardt.ablams.domain.enumeration.FileType;

/**
 * A DTO for the AudioFile entity.
 */
public class AudioFileDTO implements Serializable {

    private Long id;

    private FileType fileType;

    private String filePath;

    private Long audioLibraryId;

    private Long audioBookId;

    private Long progressId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
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

    public Long getAudioBookId() {
        return audioBookId;
    }

    public void setAudioBookId(Long audioBookId) {
        this.audioBookId = audioBookId;
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

        AudioFileDTO audioFileDTO = (AudioFileDTO) o;
        if (audioFileDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), audioFileDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AudioFileDTO{" +
            "id=" + getId() +
            ", fileType='" + getFileType() + "'" +
            ", filePath='" + getFilePath() + "'" +
            ", audioLibrary=" + getAudioLibraryId() +
            ", audioBook=" + getAudioBookId() +
            ", progress=" + getProgressId() +
            "}";
    }
}
