package de.teberhardt.ablams.service.mapper;

import de.teberhardt.ablams.domain.*;
import de.teberhardt.ablams.service.dto.AudioFileDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AudioFile and its DTO AudioFileDTO.
 */
@Mapper(componentModel = "spring", uses = {AudioBookMapper.class, ProgressableMapper.class})
public interface AudioFileMapper extends EntityMapper<AudioFileDTO, AudioFile> {

    @Mapping(source = "audioBook.id", target = "audioBookId")
    @Mapping(source = "progress.id", target = "progressId")
    AudioFileDTO toDto(AudioFile audioFile);

    @Mapping(source = "audioBookId", target = "audioBook")
    @Mapping(source = "progressId", target = "progress")
    AudioFile toEntity(AudioFileDTO audioFileDTO);

    default AudioFile fromId(Long id) {
        if (id == null) {
            return null;
        }
        AudioFile audioFile = new AudioFile();
        audioFile.setId(id);
        return audioFile;
    }
}
