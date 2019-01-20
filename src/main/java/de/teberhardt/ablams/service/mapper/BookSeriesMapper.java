package de.teberhardt.ablams.service.mapper;

import de.teberhardt.ablams.domain.*;
import de.teberhardt.ablams.service.dto.BookSeriesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity BookSeries and its DTO BookSeriesDTO.
 */
@Mapper(componentModel = "spring", uses = {AuthorMapper.class})
public interface BookSeriesMapper extends EntityMapper<BookSeriesDTO, BookSeries> {

    @Mapping(source = "author.id", target = "authorId")
    BookSeriesDTO toDto(BookSeries bookSeries);

    @Mapping(target = "audioBooks", ignore = true)
    @Mapping(source = "authorId", target = "author")
    BookSeries toEntity(BookSeriesDTO bookSeriesDTO);

    default BookSeries fromId(Long id) {
        if (id == null) {
            return null;
        }
        BookSeries bookSeries = new BookSeries();
        bookSeries.setId(id);
        return bookSeries;
    }
}
