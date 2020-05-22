package de.teberhardt.ablams.web.rest.assembler;

import de.teberhardt.ablams.web.dto.AudiobookDTO;
import de.teberhardt.ablams.web.rest.controller.AudioLibraryController;
import de.teberhardt.ablams.web.rest.controller.AudioSeriesController;
import de.teberhardt.ablams.web.rest.controller.AudiobookController;
import org.springframework.hateoas.*;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AudiobookRepresentationModelAssembler implements SimpleRepresentationModelAssembler<AudiobookDTO> {

    @Override
    public void addLinks(EntityModel<AudiobookDTO> resource) {

        AudiobookDTO abook = resource.getContent();

        if (abook != null) {

            resource.add(
                linkTo(
                    methodOn(AudiobookController.class)
                        .getAudiobook(abook.getId())
            ).withSelfRel());

            if (abook.getAudioLibraryId() != null) {
                resource.add(
                    linkTo(
                        methodOn(AudioLibraryController.class)
                            .getAudioLibrary(abook.getAudioLibraryId())
                    ).withRel("audio-library")
                );
            }

            if (resource.getContent().getSeriesId() != null) {
                resource.add(
                    linkTo(
                        methodOn(AudioSeriesController.class)
                            .getAudioSeries(abook.getSeriesId())
                    ).withRel("audio-series")
                );
            }
        }

    }

    @Override
    public void addLinks(CollectionModel<EntityModel<AudiobookDTO>> resources) {
        resources.add(
            linkTo(
                methodOn(AudiobookController.class)
                    .getAllAudiobooks(null)
            ).withSelfRel()
        );

    }
}
