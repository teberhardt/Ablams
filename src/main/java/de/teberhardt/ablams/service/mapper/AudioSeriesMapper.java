package de.teberhardt.ablams.service.mapper;

import de.teberhardt.ablams.domain.*;
import de.teberhardt.ablams.service.dto.AudioSeriesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AudioSeries and its DTO AudioSeriesDTO.
 */
@Mapper(componentModel = "spring", uses = {AuthorMapper.class})
public interface AudioSeriesMapper extends EntityMapper<AudioSeriesDTO, AudioSeries> {

    @Mapping(source = "author.id", target = "authorId")
    AudioSeriesDTO toDto(AudioSeries audioSeries);

    @Mapping(target = "audioBooks", ignore = true)
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
