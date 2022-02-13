package de.teberhardt.ablams.service.mapper;

import de.teberhardt.ablams.domain.AudioSeries;
import de.teberhardt.ablams.web.dto.AudioSeriesDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity AudioSeries and its DTO AudioSeriesDTO.
 */
@Mapper(componentModel = "cdi", uses = {AuthorMapper.class})
public interface AudioSeriesMapper extends EntityMapper<AudioSeriesDTO, AudioSeries> {

    @Mapping(source = "author.id", target = "authorId")
    AudioSeriesDTO toDto(AudioSeries audioSeries);

    @Mapping(target = "audiobooks", ignore = true)
    @Mapping(source = "authorId", target = "author")
    AudioSeries toEntity(AudioSeriesDTO audioSeriesDTO);

    default AudioSeries fromId(Long id) {
        if (id == null) {
            return null;
        }
        AudioSeries audioSeries = new AudioSeries();
        audioSeries.setId(id);
        return audioSeries;
    }
}
