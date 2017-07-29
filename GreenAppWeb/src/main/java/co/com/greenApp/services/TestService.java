package co.com.greenApp.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 * @author AddaxT
 */
@Path("/TestService")
public interface TestService {

    @GET
    @Produces("application/json")
    @Path("/prueba")
    String getGreeting();
}
