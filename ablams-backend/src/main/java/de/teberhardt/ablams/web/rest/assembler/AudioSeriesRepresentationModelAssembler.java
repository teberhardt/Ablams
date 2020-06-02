package de.teberhardt.ablams.web.rest.assembler;

import de.teberhardt.ablams.web.dto.AudioSeriesDTO;
import de.teberhardt.ablams.web.dto.AuthorDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class AudioSeriesRepresentationModelAssembler implements SimpleRepresentationModelAssembler<AudioSeriesDTO> {

    private final EntityLinks entityLinks;

    public AudioSeriesRepresentationModelAssembler(EntityLinks entityLinks) {
        this.entityLinks = entityLinks;
    }

    @Override
    public void addLinks(EntityModel<AudioSeriesDTO> resource) {

        AudioSeriesDTO content = resource.getContent();

        resource.add(
            entityLinks.linkForItemResource(content, AudioSeriesDTO::getId).withSelfRel()
        );


        if (content.getAuthorId() != null) {
            resource.add(
                entityLinks.linkToItemResource(AuthorDTO.class, content.getAuthorId())
            );
        }

    }

    @Override
    public void addLinks(CollectionModel<EntityModel<AudioSeriesDTO>> resources) {

    }
}
