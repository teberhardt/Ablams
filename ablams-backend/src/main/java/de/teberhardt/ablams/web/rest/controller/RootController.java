package de.teberhardt.ablams.web.rest.controller;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api")
public class RootController {

    @GetMapping
    ResponseEntity<RepresentationModel<?>> root() {

        RepresentationModel<?> model = new RepresentationModel<RepresentationModel<?>>();

        model.add(linkTo(methodOn(RootController.class).root()).withSelfRel());
        model.add(linkTo(methodOn(AudiobookController.class).getAllAudiobooks(null)).withRel("audio-books"));
        model.add(linkTo(methodOn(AudiofileController.class).getAllAudiofiles(null)).withRel("audio-files"));
        model.add(linkTo(methodOn(AudioLibraryController.class).getAllAudioLibraries()).withRel("audio-libraries"));
        model.add(linkTo(methodOn(AudioSeriesController.class).getAllAudioSeries()).withRel("audio-series"));
        model.add(linkTo(methodOn(AuthorController.class).getAllAuthors()).withRel("authors"));
        model.add(linkTo(methodOn(CoverController.class).getAllCovers()).withRel("covers"));
        model.add(linkTo(methodOn(ProgressableController.class).getAllProgressables()).withRel("progresses"));

        return ResponseEntity.ok(model);
    }


}
