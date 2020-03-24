package de.teberhardt.ablams.service.mapper;

import de.teberhardt.ablams.domain.Cover;
import de.teberhardt.ablams.web.dto.CoverDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity Image and its DTO ImageDTO.
 */
@Mapper(componentModel = "spring", uses = {AudioBookMapper.class, AuthorMapper.class})
public interface CoverMapper extends EntityMapper<CoverDTO, Cover> {

    @Mapping(source = "audioBook.id", target = "audioBookId")
    @Mapping(source = "author.id", target = "authorId")
    CoverDTO toDto(Cover cover);

    @Mapping(source = "audioBookId", target = "audioBook")
    @Mapping(source = "authorId", target = "author")
    Cover toEntity(CoverDTO coverDTO);

    default Cover fromId(Long id) {
        if (id == null) {
            return null;
        }
        Cover cover = new Cover();
        cover.setId(id);
        return cover;
    }
}
