package de.teberhardt.ablams.service.mapper;

import de.teberhardt.ablams.domain.*;
import de.teberhardt.ablams.service.dto.ProgressableDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Progressable and its DTO ProgressableDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProgressableMapper extends EntityMapper<ProgressableDTO, Progressable> {


    @Mapping(target = "audioFiles", ignore = true)
    Progressable toEntity(ProgressableDTO progressableDTO);

    default Progressable fromId(Long id) {
        if (id == null) {
            return null;
        }
        Progressable progressable = new Progressable();
        progressable.setId(id);
        return progressable;
    }
}
