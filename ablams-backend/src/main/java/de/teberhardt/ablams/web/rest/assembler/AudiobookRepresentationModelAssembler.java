package de.teberhardt.ablams.web.rest.assembler;

import de.teberhardt.ablams.web.dto.AudiobookDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkRelation;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class AudiobookRepresentationModelAssembler implements SimpleRepresentationModelAssembler<AudiobookDTO> {

    @Override
    public void addLinks(EntityModel<AudiobookDTO> resource) {

        AudiobookDTO abook = resource.getContent();

        if (abook != null) {

            resource.add(
                Link.of("/api/audio-books/{abookId}")
                    .withRel(LinkRelation.of("self"))
                    .expand(abook.getId()));

            if (abook.getAudioLibraryId() != null) {
                resource.add(
                    Link.of("/api/audio-libraries/{audiolibId}")
                        .withRel(
                            LinkRelation.of("audio-library")
                        ).expand(
                            abook.getAudioLibraryId()
                    )
                );
            }

            if (resource.getContent().getSeriesId() != null) {
                resource.add(
                    Link.of("/audio-series/{audioSeriesId}")
                        .withRel(
                            LinkRelation.of("audio-series")
                        ).expand(
                            abook.getSeriesId()
                        ));
            }
        }

    }

    @Override
    public void addLinks(CollectionModel<EntityModel<AudiobookDTO>> resources) {
        resources.add(
            Link.of("/api/audio-books/{abookId}")
                .withRel(LinkRelation.of("self"))
        );
    }
}
