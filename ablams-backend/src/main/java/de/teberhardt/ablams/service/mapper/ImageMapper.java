package de.teberhardt.ablams.service.mapper;

import de.teberhardt.ablams.domain.Image;
import de.teberhardt.ablams.web.dto.ImageDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity Image and its DTO ImageDTO.
 */
@Mapper(componentModel = "spring", uses = {AudioBookMapper.class, AuthorMapper.class})
public interface ImageMapper extends EntityMapper<ImageDTO, Image> {

    @Mapping(source = "audioBook.id", target = "audioBookId")
    @Mapping(source = "author.id", target = "authorId")
    ImageDTO toDto(Image image);

    @Mapping(source = "audioBookId", target = "audioBook")
    @Mapping(source = "authorId", target = "author")
    Image toEntity(ImageDTO imageDTO);

    default Image fromId(Long id) {
        if (id == null) {
            return null;
        }
        Image image = new Image();
        image.setId(id);
        return image;
    }
}
