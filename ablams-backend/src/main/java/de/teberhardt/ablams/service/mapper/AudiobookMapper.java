package de.teberhardt.ablams.service.mapper;

import de.teberhardt.ablams.domain.Audiobook;
import de.teberhardt.ablams.web.dto.AudiobookDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity Audiobook and its DTO AudiobookDTO.
 */
@Mapper(componentModel = "cdi", uses = {AudioLibraryMapper.class, AudioSeriesMapper.class, AuthorMapper.class})
public interface AudiobookMapper extends EntityMapper<AudiobookDTO, Audiobook> {

    @Mapping(source = "audioLibrary.id", target = "audioLibraryId")
    @Mapping(source = "series.id", target = "seriesId")
    @Mapping(source = "author.id", target = "authorId")
    AudiobookDTO toDto(Audiobook audiobook);

    @Mapping(target = "audiofiles", ignore = true)
    @Mapping(target = "cover", ignore = true)
    @Mapping(source = "audioLibraryId", target = "audioLibrary")
    @Mapping(source = "seriesId", target = "series")
    @Mapping(source = "authorId", target = "author")
    Audiobook toEntity(AudiobookDTO audiobookDTO);

    default Audiobook fromId(Long id) {
        if (id == null) {
            return null;
        }
        Audiobook audiobook = new Audiobook();
        audiobook.setId(id);
        return audiobook;
    }
}
