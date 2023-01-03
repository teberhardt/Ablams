package de.teberhardt.ablams.web.rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

@Path("/api/user")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @GET
    @PermitAll
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/public")
    public String publicResource() {
        return "public";
    }

    @GET
    @RolesAllowed("user")
    @Path("/me")
    public String me(@Context SecurityContext securityContext) {

        return securityContext.getUserPrincipal().getName();
    }

    @GET
    @RolesAllowed("admin")
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/admin")
    public String adminResource() {
        return "admin";
    }

    @POST
    @PermitAll
    @Path("/login")
    public String login(@Context SecurityContext securityContext) {
        log.debug("got sec context: {}", securityContext);
        log.debug("username: {}", securityContext.getUserPrincipal().getName());

        return securityContext.getUserPrincipal().getName();
    }

}
