package api;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("user")
public class User {


    @GET
    @Path("/login")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public String login(@QueryParam("userName") String userName, @QueryParam("password") String password) {
        return "Got it!";
    }


    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public String create(
            @FormParam("name") String name,
            @FormParam("dateOfBirth") String dateOfBirth,
            @FormParam("userName") String userName,
            @FormParam("password") String password,
            @FormParam("privacyPolicy") Boolean privacyPolicy
    ) {
        return "Got it!";
    }


    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Path("/message/get")
    @Produces(MediaType.APPLICATION_JSON)
    public String getMessages() {
        return "Got it!";
    }

}
