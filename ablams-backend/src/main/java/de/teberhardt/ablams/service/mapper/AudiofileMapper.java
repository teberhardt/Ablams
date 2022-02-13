package de.teberhardt.ablams.service.mapper;

import de.teberhardt.ablams.domain.Audiofile;
import de.teberhardt.ablams.web.dto.AudiofileDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity Audiofile and its DTO AudiofileDTO.
 */
@Mapper(componentModel = "cdi", uses = {AudiobookMapper.class, ProgressableMapper.class})
public interface AudiofileMapper extends EntityMapper<AudiofileDTO, Audiofile> {

    @Mapping(source = "audiobookId", target = "audiobookId")
    AudiofileDTO toDto(Audiofile audiofile);

    @Mapping(source = "audiobookId", target = "audiobookId")
    Audiofile toEntity(AudiofileDTO audiofileDTO);
}
