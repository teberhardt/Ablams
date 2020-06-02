package de.teberhardt.ablams.web.rest.assembler;

import de.teberhardt.ablams.web.dto.AudioLibraryDTO;
import de.teberhardt.ablams.web.dto.AudioSeriesDTO;
import de.teberhardt.ablams.web.dto.AudiobookDTO;
import de.teberhardt.ablams.web.rest.controller.AudiobookController;
import de.teberhardt.ablams.web.rest.controller.AudiofileController;
import org.springframework.hateoas.Affordance;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.net.URISyntaxException;
import java.util.Arrays;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

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
                        selfReference(abook),
                        audioFilesReference(abook)
           );



            if (abook.getAudioLibraryId() != null) {
                resource.add(
                    audioLibraryReference(abook.getAudioLibraryId())
                );
            }

            if (abook.getSeriesId() != null) {
                resource.add(
                    seriesReference(abook.getSeriesId())
                );
            }

        }
    }

    private Link audioFilesReference(AudiobookDTO abook) {
        return linkTo(methodOn(AudiofileController.class)
            .getAudiofilesOfAudiobook(abook.getId()))
            .withRel("audio-files");
    }

    private Link seriesReference(Long seriesId) {
        return entityLinks
            .linkToItemResource(AudioSeriesDTO.class, seriesId)
            .withRel("audio-series");
    }


    private Link selfReference(AudiobookDTO abook)
    {
        Link selfLink = entityLinks
            .linkToItemResource(abook, AudiobookDTO::getId);

        Affordance get = afford(methodOn(AudiobookController.class).getAudiobook(abook.getId()));

        Affordance deletion = afford(
            methodOn(AudiobookController.class).deleteAudiobook(abook.getId())
        );

        Affordance update = null;
        try {
            update = afford(
               methodOn(AudiobookController.class).updateAudiobook(abook)
           );
        } catch (URISyntaxException e) {
            // ignored
        }

        return selfLink
            .andAffordances(Arrays.asList( get,update, deletion))
            .withSelfRel();


    }

    private Link audioLibraryReference(Long audioLibraryId)
    {
        return entityLinks
            .linkToItemResource(AudioLibraryDTO.class, audioLibraryId )
            .withRel("audio-library");
    }

    @Override
    public void addLinks(CollectionModel<EntityModel<AudiobookDTO>> resources) {

    }
}
