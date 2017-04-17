package api;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("session")
public class Session {

    @PUT
    @Path("/token")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public String login() {
        return "Success!";
    }
}
