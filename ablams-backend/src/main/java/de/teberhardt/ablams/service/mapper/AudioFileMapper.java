package de.teberhardt.ablams.service.mapper;

import de.teberhardt.ablams.domain.AudioFile;
import de.teberhardt.ablams.web.dto.AudioFileDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity AudioFile and its DTO AudioFileDTO.
 */
@Mapper(componentModel = "spring", uses = {AudiobookMapper.class, ProgressableMapper.class})
public interface AudioFileMapper extends EntityMapper<AudioFileDTO, AudioFile> {

    @Mapping(source = "audiobook.id", target = "audiobookId")
    @Mapping(source = "progress.id", target = "progressId")
    AudioFileDTO toDto(AudioFile audioFile);

    @Mapping(source = "audiobookId", target = "audiobook")
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
