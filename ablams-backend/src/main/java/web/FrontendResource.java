package web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;


@Path("/api")
public class FrontendResource {

    private final static Logger logger = LoggerFactory.getLogger(FrontendResource.class);

    // Forwards all routes to FrontEnd except: '/', '/index.html', '/api', '/api/**'
    // Required because of 'mode: history' usage in frontend routing, see README for further details
    @GET
    @Path(value = "{_:^(?!index\\.html|api).$}")
    public String redirectApi() {
        logger.info("URL entered directly into the Browser, so we need to redirect...");
        return "forward:/";
    }
}
