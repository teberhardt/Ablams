package de.teberhardt.ablams.web.rest.assembler;

import de.teberhardt.ablams.domain.Progressable;
import de.teberhardt.ablams.web.dto.AudiobookDTO;
import de.teberhardt.ablams.web.dto.AudiofileDTO;
import de.teberhardt.ablams.web.dto.ProgressableDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Service;

@Service
public class AudiofilesRepresentationModelAssembler implements SimpleRepresentationModelAssembler<AudiofileDTO> {

    EntityLinks entityLinks;

    public AudiofilesRepresentationModelAssembler(EntityLinks entityLinks) {
        this.entityLinks = entityLinks;
    }

    @Override
    public void addLinks(EntityModel<AudiofileDTO> resource) {
        AudiofileDTO content = resource.getContent();


        if (content != null)
        {
            resource.add(
                entityLinks.linkToItemResource(AudiofileDTO.class, content.getId())
                    .withSelfRel()
            );

            if (content.getAudiobookId() != null)
            {
                resource.add(
                entityLinks.linkToItemResource(AudiobookDTO.class, content.getAudiobookId())
                    .withRel("audio-book")
                );
            }

            if (content.getProgressId() != null)
            {
                resource.add(
                    entityLinks.linkToItemResource(ProgressableDTO.class, content.getProgressId())
                        .withRel("progress")
                );
            }

        }


    }

    @Override
    public void addLinks(CollectionModel<EntityModel<AudiofileDTO>> resources) {

    }
}
