package de.teberhardt.ablams.service.mapper;

import de.teberhardt.ablams.domain.Audiofile;
import de.teberhardt.ablams.web.dto.AudiofileDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity Audiofile and its DTO AudiofileDTO.
 */
@Mapper(componentModel = "spring", uses = {AudiobookMapper.class, ProgressableMapper.class})
public interface AudiofileMapper extends EntityMapper<AudiofileDTO, Audiofile> {

    @Mapping(source = "audiobook.id", target = "audiobookId")
    @Mapping(source = "progress.id", target = "progressId")
    AudiofileDTO toDto(Audiofile audiofile);

    @Mapping(source = "audiobookId", target = "audiobook")
    @Mapping(source = "progressId", target = "progress")
    Audiofile toEntity(AudiofileDTO audiofileDTO);

    default Audiofile fromId(Long id) {
        if (id == null) {
            return null;
        }
        Audiofile audiofile = new Audiofile();
        audiofile.setId(id);
        return audiofile;
    }
}
