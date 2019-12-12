package de.teberhardt.ablams.service.mapper;

import de.teberhardt.ablams.domain.AudioBook;
import de.teberhardt.ablams.web.dto.AudioBookDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity AudioBook and its DTO AudioBookDTO.
 */
@Mapper(componentModel = "spring", uses = {AudioLibraryMapper.class, AudioSeriesMapper.class, AuthorMapper.class})
public interface AudioBookMapper extends EntityMapper<AudioBookDTO, AudioBook> {

    @Mapping(source = "audioLibrary.id", target = "audioLibraryId")
    @Mapping(source = "series.id", target = "seriesId")
    @Mapping(source = "author.id", target = "authorId")
    AudioBookDTO toDto(AudioBook audioBook);

    @Mapping(target = "audioFiles", ignore = true)
    @Mapping(target = "image", ignore = true)
    @Mapping(source = "audioLibraryId", target = "audioLibrary")
    @Mapping(source = "seriesId", target = "series")
    @Mapping(source = "authorId", target = "author")
    AudioBook toEntity(AudioBookDTO audioBookDTO);

    default AudioBook fromId(Long id) {
        if (id == null) {
            return null;
        }
        AudioBook audioBook = new AudioBook();
        audioBook.setId(id);
        return audioBook;
    }
}
