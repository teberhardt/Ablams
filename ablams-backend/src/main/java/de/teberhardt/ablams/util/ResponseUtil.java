package de.teberhardt.ablams.util;

import javax.ws.rs.core.Response;
import java.util.Optional;

/**
 * Utility class for ResponseEntity creation.
 */
public interface ResponseUtil {

    static <X> Response wrapOrNotFound(Optional<X> maybeResponse) {
        return maybeResponse.map(response -> Response
                        .ok()
                        //.headers(header)
                        .entity(response).build()
                )
                .orElse(
                        Response.status(Response.Status.NOT_FOUND).build()
                );
    }
}
