package de.teberhardt.ablams.service.mapper;

import de.teberhardt.ablams.domain.Progressable;
import de.teberhardt.ablams.web.dto.ProgressableDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity Progressable and its DTO ProgressableDTO.
 */
@Mapper(componentModel = "cdi", uses = {})
public interface ProgressableMapper extends EntityMapper<ProgressableDTO, Progressable> {

    @Mapping(source = "afileId", target = "audiofile.id")
    @Mapping(source = "abookId", target = "audiobook.id")
    Progressable toEntity(ProgressableDTO progressableDTO);

    @Mapping(source = "audiofile.id", target = "afileId")
    @Mapping(source = "audiobook.id", target = "abookId")
    ProgressableDTO toDto(Progressable progressable);

    default Progressable fromId(Long id) {
        if (id == null) {
            return null;
        }
        Progressable progressable = new Progressable();
        progressable.setId(id);
        return progressable;
    }
}
