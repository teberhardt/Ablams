package de.teberhardt.ablams.service.mapper;

import de.teberhardt.ablams.domain.Author;
import de.teberhardt.ablams.service.dto.AuthorDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity Author and its DTO AuthorDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AuthorMapper extends EntityMapper<AuthorDTO, Author> {


    @Mapping(target = "audioBooks", ignore = true)
    @Mapping(target = "audioSeries", ignore = true)
    @Mapping(target = "image", ignore = true)
    Author toEntity(AuthorDTO authorDTO);

    default Author fromId(Long id) {
        if (id == null) {
            return null;
        }
        Author author = new Author();
        author.setId(id);
        return author;
    }
}
