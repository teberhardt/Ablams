package de.teberhardt.ablams.web.rest.assembler;

import de.teberhardt.ablams.web.dto.AudioLibraryDTO;
import de.teberhardt.ablams.web.dto.AudioSeriesDTO;
import de.teberhardt.ablams.web.dto.AudiobookDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class AudiobookRepresentationModelAssembler implements SimpleRepresentationModelAssembler<AudiobookDTO> {

    private final EntityLinks entityLinks;

    public AudiobookRepresentationModelAssembler(EntityLinks entityLinks) {
        this.entityLinks = entityLinks;
    }

    @Override
    public void addLinks(EntityModel<AudiobookDTO> resource) {

        AudiobookDTO abook = resource.getContent();

        if (abook != null) {

            resource.add(
                    entityLinks
                        .linkToItemResource(abook.getClass(), abook.getId())
                        .withSelfRel()
           );

            if (abook.getAudioLibraryId() != null) {
                resource.add(
                    entityLinks
                        .linkToItemResource(AudioLibraryDTO.class, abook.getAudioLibraryId())
                        .withRel("audio-library")
                );
            }

            if (abook.getSeriesId() != null) {
                resource.add(
                    entityLinks
                        .linkToItemResource(AudioSeriesDTO.class, abook.getSeriesId())
                        .withRel("audio-series")
                );
            }
        }

    }

    @Override
    public void addLinks(CollectionModel<EntityModel<AudiobookDTO>> resources) {

    }
}
