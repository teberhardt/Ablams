package de.teberhardt.ablams.web.rest.assembler;

import de.teberhardt.ablams.web.dto.AudioLibraryDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class AudioLibraryRepresentationModelAssembler  implements SimpleRepresentationModelAssembler<AudioLibraryDTO> {

    private final EntityLinks entityLinks;

    public AudioLibraryRepresentationModelAssembler(EntityLinks entityLinks) {
        this.entityLinks = entityLinks;
    }


    @Override
    public void addLinks(EntityModel<AudioLibraryDTO> resource) {

        AudioLibraryDTO content = resource.getContent();
        if (content != null)
        {
            resource.add(
                entityLinks.linkToItemResource(content, AudioLibraryDTO::getId).withSelfRel()
            );

        }
    }

    @Override
    public void addLinks(CollectionModel<EntityModel<AudioLibraryDTO>> resources) {
        resources.add(
            entityLinks.linkFor(AudioLibraryDTO.class).withSelfRel()
        );
    }
}
