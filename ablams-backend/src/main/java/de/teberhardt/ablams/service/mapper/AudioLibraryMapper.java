package de.teberhardt.ablams.service.mapper;

import de.teberhardt.ablams.domain.AudioLibrary;
import de.teberhardt.ablams.web.dto.AudioLibraryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity AudioLibrary and its DTO AudioLibraryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AudioLibraryMapper extends EntityMapper<AudioLibraryDTO, AudioLibrary> {


    @Mapping(target = "audioBooks", ignore = true)
    AudioLibrary toEntity(AudioLibraryDTO audioLibraryDTO);

    default AudioLibrary fromId(Long id) {
        if (id == null) {
            return null;
        }
        AudioLibrary audioLibrary = new AudioLibrary();
        audioLibrary.setId(id);
        return audioLibrary;
    }
}
