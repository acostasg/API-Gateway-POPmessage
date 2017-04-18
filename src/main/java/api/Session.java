package api;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("session")
public class Session {

    @GET
    @Path("/token")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public String login(
            @HeaderParam(value = "Authorization") String authorization
    ) {
        return "Success!";
    }
}
vim .git    bi