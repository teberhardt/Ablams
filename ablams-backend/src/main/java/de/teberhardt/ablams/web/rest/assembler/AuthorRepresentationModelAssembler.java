package de.teberhardt.ablams.web.rest.assembler;

import de.teberhardt.ablams.web.dto.AuthorDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class AuthorRepresentationModelAssembler implements SimpleRepresentationModelAssembler<AuthorDTO> {

    private final EntityLinks entityLinks;

    public AuthorRepresentationModelAssembler(EntityLinks entityLinks) {
        this.entityLinks = entityLinks;
    }

    @Override
    public void addLinks(EntityModel<AuthorDTO> resource) {
        AuthorDTO content = resource.getContent();

        resource.add(
            entityLinks.linkToItemResource(content, AuthorDTO::getId )
        );

    }

    @Override
    public void addLinks(CollectionModel<EntityModel<AuthorDTO>> resources) {

    }
}
